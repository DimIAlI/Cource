package org.example.model.util;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.example.model.entity.BaseEntity;
import org.example.model.entity.account.AdminEntity;
import org.example.model.entity.account.CustomerEntity;
import org.example.model.entity.account.UserEntity;
import org.example.model.entity.space.ReservationEntity;
import org.example.model.entity.space.SpaceTypeEntity;
import org.example.model.entity.space.WorkspaceEntity;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.example.util.PropertiesUtil.*;

@UtilityClass
public class SessionManager {
    @Getter
    private final SessionFactory factory;

    static {
        DatabaseInitializer.initDatabase();
        factory = confirgureSessionFactory();
    }

    private static SessionFactory confirgureSessionFactory() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(BaseEntity.class);
        configuration.addAnnotatedClass(ReservationEntity.class);
        configuration.addAnnotatedClass(SpaceTypeEntity.class);
        configuration.addAnnotatedClass(WorkspaceEntity.class);
        configuration.addAnnotatedClass(AdminEntity.class);
        configuration.addAnnotatedClass(CustomerEntity.class);
        configuration.addAnnotatedClass(UserEntity.class);

        configuration.configure();

        return configuration.buildSessionFactory();
    }


    @UtilityClass
    private static class DatabaseInitializer {
        private final String URL_KEY = "db.init.db.url";
        private final String USER_KEY = "db.user";
        private final String PASSWORD_KEY = "db.password";
        private final String INIT_DB_NAME_KEY = "db.init.db.name";
        private final String DB_NAME_KEY = "db.new.db.name";

        @SneakyThrows
        private void initDatabase() {

            String dbName = getValue(DB_NAME_KEY);
            String url = getValue(URL_KEY) + getValue(INIT_DB_NAME_KEY);


            String checkDbQuery = "SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'";

            try (Connection connection = DriverManager.getConnection(url, getValue(USER_KEY), getValue(PASSWORD_KEY));
                 Statement statement = connection.createStatement()) {

                ResultSet resultSet = statement.executeQuery(checkDbQuery);

                if (!resultSet.next()) {
                    statement.execute("CREATE DATABASE " + dbName);
                }
            }
            String currentUrl = getValue(URL_KEY) + dbName;

            try (Connection connection = DriverManager.getConnection(currentUrl, getValue(USER_KEY), getValue(PASSWORD_KEY))) {

                initSchema(connection);
                initData(connection);
            }
        }

        @SneakyThrows
        private void initSchema(Connection connection) {
            executeSql("schema.sql", connection);
        }

        @SneakyThrows
        private void initData(Connection connection) {
            String counterQuery = "SELECT * FROM space_types";

            boolean hasResult;
            try (Statement statement = connection.createStatement()) {

                ResultSet resultSet = statement.executeQuery(counterQuery);
                hasResult = resultSet.next();
            }
            if (!hasResult) {
                executeSql("initial.sql", connection);
            }
        }

        @SneakyThrows
        private void executeSql(String fileName, Connection connection) {
            String sql = loadSqlScript(fileName);

            try (Statement statement = connection.createStatement()) {
                statement.execute(sql);
            }
        }

        @SneakyThrows
        private String loadSqlScript(String fileName) {
            try (InputStream stream = SessionManager.class.getClassLoader().getResourceAsStream(fileName)) {
                if (stream == null) {
                    throw new IllegalArgumentException("File not found: " + fileName);
                }
                return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            }
        }
    }
}


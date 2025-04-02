package org.example;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Component
public class DatabaseInitializer {
    private final String url;
    private final String user;
    private final String password;
    private final String initDbName;
    private final String dbName;

    DatabaseInitializer(@Value("${db.init.db.url}") String url,
                               @Value("${db.user}") String user,
                               @Value("${db.password}") String password,
                               @Value("${db.init.db.name}") String initDbName,
                               @Value("${db.new.db.name}") String dbName) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.initDbName = initDbName;
        this.dbName = dbName;
    }

    @SneakyThrows
    void initDatabase() {

        String url = this.url + initDbName;

        String checkDbQuery = "SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(checkDbQuery);

            if (!resultSet.next()) {
                statement.execute("CREATE DATABASE " + dbName);
            }
        }
        String currentUrl = this.url + dbName;

        try (Connection connection = DriverManager.getConnection(currentUrl, user, password)) {

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
        try (InputStream stream = DatabaseInitializer.class.getClassLoader().getResourceAsStream(fileName)) {
            if (stream == null) {
                throw new IllegalArgumentException("File not found: " + fileName);
            }
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}

package org.example.model.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.example.util.PropertiesUtil.*;

@UtilityClass
public class ConnectionManager {

    private final String URL_KEY = "db.url";
    private final String USER_KEY = "db.user";
    private final String PASSWORD_KEY = "db.password";
    private final String POOL_SIZE_KEY = "db.pool.size";
    private final String INIT_DB_KEY = "db.init.db.name";
    private final String DB_NAME_KEY = "db.new.db.name";
    private final Integer DEFAULT_POOL_SIZE = 5;

    private BlockingQueue<Connection> proxyPool;
    private List<Connection> connectionPool;

    static {
        DatabaseInitializer.initDatabase();
        initPool();
        DatabaseInitializer.initSchema();
        DatabaseInitializer.initData();
    }

    public Connection get() {
        try {
            return proxyPool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread was interrupted while waiting for a connection from the pool", e);
        }
    }

    @SneakyThrows
    public void closePool() {
        for (Connection connection : connectionPool) {
            connection.close();
        }
    }

    private void initPool() {

        int capacity = getValue(POOL_SIZE_KEY) != null
                ? Integer.parseInt(getValue(POOL_SIZE_KEY))
                : DEFAULT_POOL_SIZE;

        proxyPool = new ArrayBlockingQueue<>(capacity);
        connectionPool = new ArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            Connection realConnection = open();
            Connection proxyConnection = createProxyConnection(realConnection);

            proxyPool.add(proxyConnection);
            connectionPool.add(realConnection);
        }
    }

    private Connection createProxyConnection(Connection realConnection) {
        return (Connection) Proxy.newProxyInstance(realConnection.getClass().getClassLoader(), new Class[]{Connection.class},
                (proxy, method, args) -> method.getName().equals("close")
                        ? proxyPool.add((Connection) proxy)
                        : method.invoke(realConnection, args));
    }

    @SneakyThrows
    private Connection open() {
        String connectionKey = getValue(URL_KEY) + getValue(DB_NAME_KEY);

        return DriverManager.getConnection(
                connectionKey,
                getValue(USER_KEY),
                getValue(PASSWORD_KEY)
        );
    }

    @UtilityClass
    private static class DatabaseInitializer {

        @SneakyThrows
        private void initDatabase() {

            String connectionKey = getValue(URL_KEY) + getValue(INIT_DB_KEY);
            String dbName = getValue(DB_NAME_KEY);
            String checkDbQuery = "SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'";

            try (Connection connection = DriverManager.getConnection(connectionKey, getValue(USER_KEY), getValue(PASSWORD_KEY));
                 Statement statement = connection.createStatement()) {

                ResultSet resultSet = statement.executeQuery(checkDbQuery);

                if (!resultSet.next()) {
                    statement.execute("CREATE DATABASE " + dbName);
                }
            }
        }

        @SneakyThrows
        private void initSchema() {
            executeSql("schema.sql");
        }

        @SneakyThrows
        private void initData() {
            String counterQuery = "SELECT * FROM space_types";

            try (Connection connection = get();
                 Statement statement = connection.createStatement()) {

                ResultSet resultSet = statement.executeQuery(counterQuery);
                if (!resultSet.next()) {
                    executeSql("initial.sql");
                }
            }
        }

        @SneakyThrows
        private void executeSql(String fileName) {
            String sql = loadSqlScript(fileName);

            try (Connection connection = get();
                 Statement statement = connection.createStatement()) {
                statement.execute(sql);
            }
        }

        @SneakyThrows
        private String loadSqlScript(String fileName) {
            try (InputStream stream = ConnectionManager.class.getClassLoader().getResourceAsStream(fileName)) {
                if (stream == null) {
                    throw new IllegalArgumentException("File not found: " + fileName);
                }
                return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            }
        }
    }
}


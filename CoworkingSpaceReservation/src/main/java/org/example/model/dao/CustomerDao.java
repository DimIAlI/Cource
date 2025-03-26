package org.example.model.dao;

import lombok.SneakyThrows;
import org.example.model.entity.CustomerEntity;

import java.sql.ResultSet;

public class CustomerDao extends AbstractUserDao<CustomerEntity> {

    private static final CustomerDao INSTANCE = new CustomerDao();
    private static final String SAVE_SQL = """
            INSERT INTO customers (login) VALUES (?)
            RETURNING id
            """;

    private static final String FIND_BY_LOGIN_SQL = """
            SELECT id, login FROM customers where login = ?;
            """;

    private CustomerDao() {
    }

    public static CustomerDao getInstance() {
        return INSTANCE;
    }

    @Override
    protected String getSaveSql() {
        return SAVE_SQL;
    }

    @Override
    protected String getFindByLoginSql() {
        return FIND_BY_LOGIN_SQL;
    }

    @SneakyThrows
    @Override
    protected CustomerEntity buildEntity(ResultSet resultSet) {
        return CustomerEntity.builder()
                .id(resultSet.getLong("id"))
                .login(resultSet.getString("login"))
                .build();
    }
}

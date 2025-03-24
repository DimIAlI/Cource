package org.example.model.dao;

import lombok.SneakyThrows;
import org.example.model.entity.AdminEntity;

import java.sql.ResultSet;


public class AdminDao extends AbstractUserDao<AdminEntity> {

    private static final AdminDao INSTANCE = new AdminDao();
    private static final String SAVE_SQL = """
            INSERT INTO admins (login) VALUES (?)
            RETURNING id
            """;

    private static final String FIND_BY_LOGIN_SQL = """
            SELECT id, login FROM admins where login = ?;
            """;

    private AdminDao() {
    }

    public static AdminDao getInstance() {
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
    protected AdminEntity buildEntity(ResultSet resultSet) {
        return AdminEntity.builder()
                .id(resultSet.getLong("id"))
                .login(resultSet.getString("login"))
                .build();
    }
}

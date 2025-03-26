package org.example.model.dao;

import lombok.SneakyThrows;
import org.example.model.entity.UserEntity;
import org.example.model.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public abstract class AbstractUserDao<T extends UserEntity> implements UserDao<T> {

    @Override
    @SneakyThrows
    public T save(T user) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(getSaveSql())) {

            statement.setString(1, user.getLogin());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
            }
            return user;
        }
    }

    @Override
    @SneakyThrows
    public Optional<T> findByLogin(String login) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(getFindByLoginSql())) {

            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(buildEntity(resultSet));
            }
            return Optional.empty();
        }
    }

    protected abstract String getSaveSql();

    protected abstract String getFindByLoginSql();


    protected abstract T buildEntity(ResultSet resultSet) throws SQLException;
}

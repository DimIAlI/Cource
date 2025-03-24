package org.example.model.dao;

import lombok.SneakyThrows;
import org.example.model.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T> implements Dao<T> {

    @SneakyThrows
    @Override
    public T save(T entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(getSaveSql())) {

            setSaveStatementParams(preparedStatement, entity);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            return setGeneratedKey(entity, resultSet.getLong("id"));
        }
    }

    @SneakyThrows
    @Override
    public boolean delete(long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(getDeleteSql())) {

            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @SneakyThrows
    @Override
    public List<T> getAll() {

        List<T> entities = new ArrayList<>();

        try (Connection connection = ConnectionManager.get();
             Statement statement = connection.createStatement();

             ResultSet resultSet = statement.executeQuery(getFindAllSql())) {

            while (resultSet.next()) {
                entities.add(buildEntity(resultSet));
            }
        }
        return entities;
    }

    protected abstract String getSaveSql();

    protected abstract String getDeleteSql();

    protected abstract String getFindAllSql();

    protected abstract T buildEntity(ResultSet resultSet) throws SQLException;

    protected abstract void setSaveStatementParams(PreparedStatement preparedStatement, T entity) throws SQLException;

    protected abstract T setGeneratedKey(T entity, long id);
}


package org.example.model.dao;

import lombok.SneakyThrows;
import org.example.model.dto.filters.Filter;
import org.example.model.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T> implements Dao<T> {

    @Override
    public T save(T entity) throws SQLException {
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

    @SneakyThrows
    @Override
    public List<T> getAllWithFilter(Filter filter) {

        List<Object> parameters = new ArrayList<>();
        List<T> entities = new ArrayList<>();

        String resultWhereCondition = getFindAllSql() + filter.buildWhereCondition(parameters);

        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(resultWhereCondition)) {

            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }

            ResultSet resultSet = statement.executeQuery();
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


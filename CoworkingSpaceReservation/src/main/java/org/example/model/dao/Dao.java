package org.example.model.dao;

import org.example.model.dto.filters.Filter;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {

    T save(T entity) throws SQLException;

    boolean delete(long id);

    List<T> getAll();

    List<T> getAllWithFilter(Filter filter);
}

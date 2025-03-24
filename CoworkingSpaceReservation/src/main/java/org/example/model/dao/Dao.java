package org.example.model.dao;

import java.util.List;

public interface Dao<T> {

    T save(T entity);

    boolean delete(long id);

    List<T> getAll();
}

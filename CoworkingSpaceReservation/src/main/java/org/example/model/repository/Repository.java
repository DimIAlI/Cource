package org.example.model.repository;

import org.example.model.dto.filters.Filter;
import org.example.model.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

public interface Repository<T extends Serializable, E extends BaseEntity, F extends Filter<T>> {

    E save(E entity);

    List<E> findAll(F filter);
}

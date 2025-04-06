package org.example.repository;


import org.example.entity.BaseEntity;
import org.example.service.filters.Filter;

import java.io.Serializable;
import java.util.List;

public interface Repository<T extends Serializable, E extends BaseEntity, F extends Filter<T>> {

    E save(E entity);

    List<E> findAll(F filter);
}

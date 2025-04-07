package org.example.repository.space;

import org.example.entity.BaseEntity;
import org.example.repository.Repository;
import org.example.service.filters.Filter;

import java.io.Serializable;
import java.util.List;

public interface SpaceRepository<T extends Serializable, E extends BaseEntity, F extends Filter<T>> extends Repository<T, E, F> {

    void delete(E entity);

    List<E> findAll();
}

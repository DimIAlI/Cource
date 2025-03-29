package org.example.model.repository.space;

import org.example.model.dto.filters.Filter;
import org.example.model.entity.BaseEntity;
import org.example.model.repository.Repository;

import java.io.Serializable;
import java.util.List;

public interface SpaceRepository<T extends Serializable, E extends BaseEntity, F extends Filter<T>> extends Repository<T, E, F> {

    void delete(E entity);

    List<E> findAll();
}

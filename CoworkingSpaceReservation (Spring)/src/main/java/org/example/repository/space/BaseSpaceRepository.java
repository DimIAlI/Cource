package org.example.repository.space;

import jakarta.persistence.EntityManager;
import org.example.entity.BaseEntity;
import org.example.repository.BaseRepository;
import org.example.service.filters.Filter;

import java.io.Serializable;
import java.util.List;

public abstract class BaseSpaceRepository<T extends Serializable, E extends BaseEntity, F extends Filter<T>>
        extends BaseRepository<T, E, F>
        implements SpaceRepository<T, E, F> {

    protected BaseSpaceRepository(EntityManager manager, Class<E> clazz) {
        super(manager, clazz);
    }

    @Override
    public void delete(E entity) {
        getManager().remove(entity);
    }

    @Override
    public List<E> findAll() {
        String jpql = "SELECT e FROM " + getClazz().getSimpleName() + " e";
        return getManager().createQuery(jpql, getClazz()).getResultList();
    }
}

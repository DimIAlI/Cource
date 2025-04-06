package org.example.repository.account;

import jakarta.persistence.EntityManager;
import org.example.entity.BaseEntity;
import org.example.repository.BaseRepository;
import org.example.service.filters.Filter;

import java.io.Serializable;

public abstract class BaseUserRepository<T extends Serializable, E extends BaseEntity, F extends Filter<T>>
        extends BaseRepository<T, E, F>
        implements UserRepository<T, E, F> {

    protected BaseUserRepository(EntityManager manager, Class<E> clazz) {
        super(manager, clazz);
    }
}

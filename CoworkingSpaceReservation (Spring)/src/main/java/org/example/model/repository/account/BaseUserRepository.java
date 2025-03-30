package org.example.model.repository.account;

import org.example.model.dto.filters.Filter;
import org.example.model.entity.BaseEntity;
import org.example.model.repository.BaseRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

public abstract class BaseUserRepository<T extends Serializable, E extends BaseEntity, F extends Filter<T>>
        extends BaseRepository<T, E, F>
        implements UserRepository<T, E, F> {
    protected BaseUserRepository(EntityManager manager, Class<E> clazz) {
        super(manager, clazz);
    }
}

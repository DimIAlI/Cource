package org.example.repository.account;

import org.example.entity.BaseEntity;
import org.example.repository.Repository;
import org.example.service.filters.Filter;

import java.io.Serializable;

public interface UserRepository<T extends Serializable, E extends BaseEntity, F extends Filter<T>> extends Repository<T, E, F> {
}

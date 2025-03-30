package org.example.model.repository.account;

import org.example.model.dto.filters.Filter;
import org.example.model.entity.BaseEntity;
import org.example.model.repository.Repository;

import java.io.Serializable;

public interface UserRepository<T extends Serializable, E extends BaseEntity, F extends Filter<T>> extends Repository<T, E, F> {
}

package org.example.model.repository;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.model.dto.filters.Filter;
import org.example.model.entity.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class BaseRepository<T extends Serializable, E extends BaseEntity, F extends Filter<T>>
        implements Repository<T, E, F> {

    private final EntityManager manager;
    private final Class<E> clazz;

    @Override
    public E save(E entity) {
        manager.persist(entity);
        return entity;
    }

    @Override
    public List<E> findAll(F filter) {
        List<Object> parameters = new ArrayList<>();
        String whereClause = filter.buildWhereCondition(parameters);

        String jpql = "SELECT e FROM " + clazz.getSimpleName() + " e " + whereClause;
        TypedQuery<E> query = manager.createQuery(jpql, clazz);

        for (int i = 0; i < parameters.size(); i++) {
            query.setParameter(i + 1, parameters.get(i));
        }
        return query.getResultList();
    }
}

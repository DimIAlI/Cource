package org.example.model.repository.space;

import org.example.model.entity.space.SpaceTypeEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
@Repository
public class SpaceTypeRepository {
    private final EntityManager manager;

    public SpaceTypeRepository(EntityManager manager) {
        this.manager = manager;
    }

    public List<SpaceTypeEntity> findAll() {
        String jpql = "SELECT e FROM " + SpaceTypeEntity.class.getSimpleName() + " e";
        return manager.createQuery(jpql, SpaceTypeEntity.class).getResultList();
    }
}

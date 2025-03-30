package org.example.model.repository.space;

import org.example.model.dto.filters.space.ReservationFilter;
import org.example.model.entity.space.ReservationEntity;

import javax.persistence.EntityManager;

public class ReservationRepository extends BaseSpaceRepository<Long, ReservationEntity, ReservationFilter> {

    public ReservationRepository(EntityManager manager) {
        super(manager, ReservationEntity.class);
    }

}

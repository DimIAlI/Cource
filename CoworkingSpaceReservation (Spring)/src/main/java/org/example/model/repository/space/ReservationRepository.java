package org.example.model.repository.space;

import org.example.model.dto.filters.space.ReservationFilter;
import org.example.model.entity.space.ReservationEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
@Repository
public class ReservationRepository extends BaseSpaceRepository<Long, ReservationEntity, ReservationFilter> {

    public ReservationRepository(EntityManager manager) {
        super(manager, ReservationEntity.class);
    }

}

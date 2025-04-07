package org.example.repository.space;

import jakarta.persistence.EntityManager;
import org.example.entity.space.ReservationEntity;
import org.example.service.filters.space.ReservationFilter;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository extends BaseSpaceRepository<Long, ReservationEntity, ReservationFilter> {

    public ReservationRepository(EntityManager manager) {
        super(manager, ReservationEntity.class);
    }

}

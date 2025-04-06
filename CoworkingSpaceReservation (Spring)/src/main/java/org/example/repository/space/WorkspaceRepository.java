package org.example.repository.space;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.entity.space.WorkspaceEntity;
import org.example.service.filters.space.WorkspaceFilter;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class WorkspaceRepository extends BaseSpaceRepository<Long, WorkspaceEntity, WorkspaceFilter> {

    public WorkspaceRepository(EntityManager manager) {
        super(manager, WorkspaceEntity.class);
    }

    public List<WorkspaceEntity> getAvailableBetween(LocalDateTime startTime, LocalDateTime endTime) {

        String jpql = "SELECT w FROM " + getClazz().getSimpleName() + " w WHERE w.id NOT IN (" +
                      "    SELECT r.space.id FROM ReservationEntity r " +
                      "    WHERE r.startTime < :end_time " +
                      "      AND r.endTime > :start_time" +
                      ")";

        TypedQuery<WorkspaceEntity> query = getManager().createQuery(jpql, WorkspaceEntity.class);
        query.setParameter("start_time", startTime);
        query.setParameter("end_time", endTime);

        return query.getResultList();
    }
}

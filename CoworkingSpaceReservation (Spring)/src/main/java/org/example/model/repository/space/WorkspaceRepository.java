package org.example.model.repository.space;

import org.example.model.dto.filters.space.WorkspaceFilter;
import org.example.model.entity.space.WorkspaceEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

public class WorkspaceRepository extends BaseSpaceRepository<Long, WorkspaceEntity, WorkspaceFilter> {

    public WorkspaceRepository(EntityManager manager) {
        super(manager, WorkspaceEntity.class);
    }

    public void update(WorkspaceEntity workspace) {
        getManager().merge(workspace);
    }

    public List<WorkspaceEntity> getAvailableBetween(LocalDateTime startTime, LocalDateTime endTime) {

        String jpql = "SELECT w FROM " + getClazz().getSimpleName() + " w WHERE w.id NOT IN (" +
                      "    SELECT r.space.id FROM ReservationEntity r " +
                      "    WHERE NOT (r.endTime <= :end_time OR r.startTime >= :start_time)" +
                      ")";

        TypedQuery<WorkspaceEntity> query = getManager().createQuery(jpql, WorkspaceEntity.class);
        query.setParameter("start_time", startTime);
        query.setParameter("end_time", endTime);

        return query.getResultList();
    }
}

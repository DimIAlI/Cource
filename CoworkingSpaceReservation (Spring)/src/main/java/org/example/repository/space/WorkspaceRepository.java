package org.example.repository.space;

import org.example.entity.space.WorkspaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WorkspaceRepository extends JpaRepository<WorkspaceEntity, Long> {

    Optional<WorkspaceEntity> findByTypeIdAndPrice(Long typeId, Double price);
    @Query("""
           SELECT w FROM WorkspaceEntity w 
           WHERE w.id NOT IN (
               SELECT r.space.id FROM ReservationEntity r 
               WHERE r.startTime < :endTime 
                 AND r.endTime > :startTime
           )
           """)
    List<WorkspaceEntity> findAvailableBetween(LocalDateTime startTime, LocalDateTime endTime);
}

package org.example.repository.space;

import org.example.entity.space.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    Optional<ReservationEntity> findByIdAndCustomerId(Long id, Long customer_id);

    List<ReservationEntity> findByCustomerId(Long customerId);
}

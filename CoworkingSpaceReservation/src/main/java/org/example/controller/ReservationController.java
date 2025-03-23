package org.example.controller;

import org.example.model.entity.CustomerEntity;
import org.example.model.entity.ReservationEntity;
import org.example.model.entity.UserEntity;
import org.example.model.entity.WorkspaceEntity;
import org.example.model.service.ReservationManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


class ReservationController {
    private final ReservationManager reservationManager;

    ReservationController() {
        this.reservationManager = ReservationManager.getInstance();
    }

    static ReservationController createReservationController() {
        return new ReservationController();
    }

    Map<Long, ReservationEntity> getAllReservations() {
        return reservationManager.getAll();
    }

    void addReservation(UserEntity currentUser, WorkspaceEntity workspace, LocalDateTime startTime, LocalDateTime endTime) {
        reservationManager.add((CustomerEntity) currentUser, workspace, startTime, endTime);
    }

    Optional<List<ReservationEntity>> getUserReservations(UserEntity currentUser) {
        return reservationManager.getCustomerReservations((CustomerEntity) currentUser);
    }

    void cancelReservation(long id, UserEntity currentUser) {
        reservationManager.remove(id, (CustomerEntity) currentUser);
    }
}

package org.example.controller;

import org.example.model.*;

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

    Map<Long, Reservation> getAllReservations() {
        return reservationManager.getAll();
    }

    void addReservation(User currentUser, Workspace workspace, LocalDateTime startTime, LocalDateTime endTime) {
        reservationManager.add((Customer) currentUser, workspace, startTime, endTime);
    }

    Optional<List<Reservation>> getUserReservations(User currentUser) {
        return reservationManager.getCustomerReservations((Customer) currentUser);
    }

    void cancelReservation(long id, User currentUser) {
        reservationManager.remove(id, (Customer) currentUser);
    }
}

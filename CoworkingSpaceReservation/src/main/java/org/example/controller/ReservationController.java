package org.example.controller;

import org.example.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class ReservationController {
    private final ReservationManager reservationManager;

    public ReservationController() {
        this.reservationManager = ReservationManager.getInstance();
    }
    static ReservationController createReservationController() {
        return new ReservationController();
    }

    public Map<Long, Reservation> getAllReservations() {
        return reservationManager.getAll();
    }

    public void addReservation(User currentUser, Workspace workspace, LocalDateTime startTime, LocalDateTime endTime) {
        reservationManager.add((Customer) currentUser, workspace, startTime, endTime);
    }

    public Optional<List<Reservation>> getUserReservations(User currentUser) {
        return reservationManager.getCustomerReservations((Customer) currentUser);
    }

    public void cancelReservation(long id, User currentUser) {
        reservationManager.remove(id, (Customer) currentUser);
    }
}

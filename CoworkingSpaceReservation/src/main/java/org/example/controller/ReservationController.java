package org.example.controller;

import org.example.model.dto.CustomerDto;
import org.example.model.dto.ReservationDto;
import org.example.model.dto.UserDto;
import org.example.model.dto.WorkspaceDto;
import org.example.model.service.ReservationManager;

import java.time.LocalDateTime;
import java.util.List;

class ReservationController {
    private final ReservationManager reservationManager;

    ReservationController() {
        this.reservationManager = ReservationManager.getInstance();
    }

    static ReservationController createReservationController() {
        return new ReservationController();
    }

    List<ReservationDto> getAllReservations() {
        return reservationManager.getAll();
    }

    void addReservation(UserDto currentUser, WorkspaceDto workspace, LocalDateTime startTime, LocalDateTime endTime) {
        reservationManager.add((CustomerDto) currentUser, workspace, startTime, endTime);
    }

    List<ReservationDto> getUserReservations(UserDto currentUser) {
        return reservationManager.getCustomerReservations((CustomerDto) currentUser);
    }

    void cancelReservation(long id, UserDto currentUser) {
        reservationManager.remove(id, (CustomerDto) currentUser);
    }
}

package org.example.controller;

import org.example.model.dto.account.CustomerDto;
import org.example.model.dto.space.ReservationDto;
import org.example.model.dto.account.UserDto;
import org.example.model.dto.space.WorkspaceDto;
import org.example.model.service.ReservationManager;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

@Controller
class ReservationController {
    private final ReservationManager reservationManager;

    ReservationController(ReservationManager reservationManager) {
        this.reservationManager = reservationManager;
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

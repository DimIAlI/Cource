package org.example.model.service;

import lombok.Getter;
import org.example.exceptions.ReservationAlreadyExistException;
import org.example.exceptions.ReservationDoesNotExistException;
import org.example.exceptions.UnauthorizedReservationAccessException;
import org.example.model.entity.CustomerEntity;
import org.example.model.entity.ReservationEntity;
import org.example.model.entity.WorkspaceEntity;

import java.time.LocalDateTime;
import java.util.*;


public class ReservationManager {

    private static final ReservationManager INSTANCE = new ReservationManager();
    private final Map<Long, ReservationEntity> RESERVATIONS_ID;
    private final Map<String, List<ReservationEntity>> RESERVATIONS_CUSTOMER;
    @Getter
    private Long id;

    private ReservationManager() {

    }

    public static ReservationManager getInstance() {
        return INSTANCE;
    }

    public void add(CustomerEntity customer, WorkspaceEntity space, LocalDateTime startTime, LocalDateTime endTime) {

        if (isWorkSpaceAvailable(space, startTime, endTime)) {

            ReservationEntity reservation = buildReservation(customer, space, startTime, endTime);

            space.setAvailable(false);

            RESERVATIONS_ID.put(reservation.getId(), reservation);

            String login = customer.getLogin();

            RESERVATIONS_CUSTOMER.putIfAbsent(login, new ArrayList<>());
            RESERVATIONS_CUSTOMER.get(login).add(reservation);
        } else throw new ReservationAlreadyExistException();
    }

    public void remove(long id, CustomerEntity currentUser) {
        ReservationEntity reservation = RESERVATIONS_ID.get(id);

        if (reservation == null) {
            throw new ReservationDoesNotExistException(id);
        }

        if (!reservation.getCustomer().equals(currentUser)) {
            throw new UnauthorizedReservationAccessException(id);
        }
        RESERVATIONS_ID.remove(id);

        String login = reservation.getCustomer().getLogin();
        List<ReservationEntity> reservations = RESERVATIONS_CUSTOMER.get(login);

        if (reservations != null) {
            reservations.remove(reservation);

            if (reservations.isEmpty()) {
                RESERVATIONS_CUSTOMER.remove(login);
            }
        }

        WorkspaceEntity workspace = reservation.getSpace();

        boolean hasOtherReservations = RESERVATIONS_ID.values().stream()
                .anyMatch(reserv -> reserv.getSpace().equals(workspace));

        if (!hasOtherReservations) {
            workspace.setAvailable(true);
        }
    }

    public Optional<List<ReservationEntity>> getCustomerReservations(CustomerEntity customer) {
        return Optional.ofNullable(RESERVATIONS_CUSTOMER.get(customer.getLogin()));
    }

    public Map<Long, ReservationEntity> getAll() {
        return RESERVATIONS_ID;
    }

    private boolean isWorkSpaceAvailable(WorkspaceEntity space, LocalDateTime startTime, LocalDateTime endTime) {

        for (ReservationEntity reservation : RESERVATIONS_ID.values()) {

            if (reservation.getSpace().equals(space)) {
                if (startTime.isBefore(reservation.getEndTime()) && endTime.isAfter(reservation.getStartTime())) {
                    return false;
                }
            }
        }
        return true;
    }

    private ReservationEntity buildReservation(CustomerEntity customer, WorkspaceEntity space, LocalDateTime startTime, LocalDateTime endTime) {
        return ReservationEntity.builder()
                .id(generateId())
                .customer(customer)
                .space(space)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    private long generateId() {
        return ++id;
    }
}

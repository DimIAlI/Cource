package org.example.model;

import lombok.Getter;
import org.example.exceptions.ReservationAlreadyExistException;
import org.example.exceptions.ReservationDoesNotExistException;
import org.example.exceptions.UnauthorizedReservationAccessException;
import org.example.model.storage.ApplicationState;
import org.example.model.storage.ApplicationStateManager;

import java.time.LocalDateTime;
import java.util.*;


public class ReservationManager {

    private static final ReservationManager INSTANCE = new ReservationManager();
    private final Map<Long, Reservation> RESERVATIONS_ID;
    private final Map<String, List<Reservation>> RESERVATIONS_CUSTOMER;
    @Getter
    private Long id;

    private ReservationManager() {
        ApplicationState appState = ApplicationStateManager.getInstance().getState();

        RESERVATIONS_ID = appState.getReservationsById();
        RESERVATIONS_CUSTOMER = appState.getReservationsByCustomer();
        id = appState.getLastReservationId();
    }

    public static ReservationManager getInstance() {
        return INSTANCE;
    }

    public void add(Customer customer, Workspace space, LocalDateTime startTime, LocalDateTime endTime) {

        if (isWorkSpaceAvailable(space, startTime, endTime)) {

            Reservation reservation = buildReservation(customer, space, startTime, endTime);

            space.setAvailable(false);

            RESERVATIONS_ID.put(reservation.getId(), reservation);

            String login = customer.getLogin();

            RESERVATIONS_CUSTOMER.putIfAbsent(login, new ArrayList<>());
            RESERVATIONS_CUSTOMER.get(login).add(reservation);
        } else throw new ReservationAlreadyExistException();
    }

    public void remove(long id, Customer currentUser) {
        Reservation reservation = RESERVATIONS_ID.get(id);

        if (reservation == null) {
            throw new ReservationDoesNotExistException(id);
        }

        if (!reservation.getCustomer().equals(currentUser)) {
            throw new UnauthorizedReservationAccessException(id);
        }
        RESERVATIONS_ID.remove(id);

        String login = reservation.getCustomer().getLogin();
        List<Reservation> reservations = RESERVATIONS_CUSTOMER.get(login);

        if (reservations != null) {
            reservations.remove(reservation);

            if (reservations.isEmpty()) {
                RESERVATIONS_CUSTOMER.remove(login);
            }
        }

        Workspace workspace = reservation.getSpace();

        boolean hasOtherReservations = RESERVATIONS_ID.values().stream()
                .anyMatch(reserv -> reserv.getSpace().equals(workspace));

        if (!hasOtherReservations) {
            workspace.setAvailable(true);
        }
    }

    public Optional<List<Reservation>> getCustomerReservations(Customer customer) {
        return Optional.ofNullable(RESERVATIONS_CUSTOMER.get(customer.getLogin()));
    }

    public Map<Long, Reservation> getAll() {
        return RESERVATIONS_ID;
    }

    private boolean isWorkSpaceAvailable(Workspace space, LocalDateTime startTime, LocalDateTime endTime) {

        for (Reservation reservation : RESERVATIONS_ID.values()) {

            if (reservation.getSpace().equals(space)) {
                if (startTime.isBefore(reservation.getEndTime()) && endTime.isAfter(reservation.getStartTime())) {
                    return false;
                }
            }
        }
        return true;
    }

    private Reservation buildReservation(Customer customer, Workspace space, LocalDateTime startTime, LocalDateTime endTime) {
        return Reservation.builder()
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

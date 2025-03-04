package org.example.model;

import org.example.exceptions.ReservationAlreadyExistException;
import org.example.exceptions.ReservationDoesNotExistException;
import org.example.exceptions.UnauthorizedReservationAccessException;
import org.example.model.storage.ApplicationStateManager;

import java.time.LocalDateTime;
import java.util.*;

public class ReservationManager {

    private static final ReservationManager INSTANCE = new ReservationManager();
    private final Map<Long, Reservation> RESERVATIONS_ID;
    private final Map<Customer, List<Reservation>> RESERVATIONS_CUSTOMER;
    private long id;

    private ReservationManager() {
        RESERVATIONS_ID = ApplicationStateManager.getInstance().getState().getReservationsById();
        RESERVATIONS_CUSTOMER = ApplicationStateManager.getInstance().getState().getReservationsByCustomer();
    }

    public static ReservationManager getInstance() {
        return INSTANCE;
    }

//todo remove getReservation functionality
    public void add(Customer customer, Workspace space, LocalDateTime startTime, LocalDateTime endTime) {

        if (isWorkSpaceAvailable(space, startTime, endTime)) {

            Reservation reservation = buildReservation(customer, space, startTime, endTime);
            List<Reservation> reservations = space.getReservations();
            reservations.add(reservation);

            space.setAvailable(false);

            RESERVATIONS_ID.put(reservation.getId(), reservation);

            RESERVATIONS_CUSTOMER.putIfAbsent(customer, new ArrayList<>());
            RESERVATIONS_CUSTOMER.get(customer).add(reservation);
        } else throw new ReservationAlreadyExistException();
    }
    //todo remove getReservation functionality
    private boolean isWorkSpaceAvailable(Workspace space, LocalDateTime startTime, LocalDateTime endTime) {
        List<Reservation> reservations = space.getReservations();

        if (reservations == null || reservations.isEmpty()) {
            return true;
        }
        for (Reservation reservation : reservations) {
            if (startTime.isBefore(reservation.getEndTime()) && endTime.isAfter(reservation.getStartTime())) {
                return false;
            }
        }
        return true;
    }
    //todo remove getReservation functionality
    public void remove(long id, Customer currentUser) {
        Reservation reservation = RESERVATIONS_ID.get(id);

        if (reservation == null) {
            throw new ReservationDoesNotExistException(id);
        }

        if (!reservation.getCustomer().equals(currentUser)) {
            throw new UnauthorizedReservationAccessException(id);
        }
        RESERVATIONS_ID.remove(id);

        Workspace space = reservation.getSpace();
        space.getReservations().remove(reservation);

        Customer customer = reservation.getCustomer();
        List<Reservation> reservations = RESERVATIONS_CUSTOMER.get(customer);

        if (reservations != null) {
            reservations.remove(reservation);

            if (reservations.isEmpty()) {
                RESERVATIONS_CUSTOMER.remove(customer);
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
        return Optional.ofNullable(RESERVATIONS_CUSTOMER.get(customer));
    }

    public Map<Long, Reservation> getAll() {
        return RESERVATIONS_ID;
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

package org.example.view;

import org.example.model.dto.space.ReservationDto;
import org.example.model.dto.space.WorkspaceDto;

import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.example.util.PropertiesUtil.*;

public class CustomerView {

    public void printWelcomeMessage() {
        System.out.println(getValue("customer.welcome"));
        System.out.println(getValue("common.login.rules"));
        System.out.print(getValue("customer.enter.username"));
    }

    public void printMenu() {
        System.out.println(getValue("customer.menu"));
        System.out.println(getValue("customer.menu.option1"));
        System.out.println(getValue("customer.menu.option2"));
        System.out.println(getValue("customer.menu.option3"));
        System.out.println(getValue("customer.menu.option4"));
        System.out.println(getValue("customer.menu.option5"));

        System.out.print(getValue("common.enter.choice"));
    }

    public void printReservationStartDateMessage() {
        System.out.print(getValue("customer.enter.start.date"));
    }

    public void printAvailableSpaces(List<WorkspaceDto> availableSpaces) {
        System.out.println(getValue("customer.available.spaces"));

        System.out.println(getValue("customer.space.separator"));
        String tableHeaderFormat = getValue("customer.space.format.table");
        System.out.printf(tableHeaderFormat, "ID", "Type", "Price");
        System.out.println(getValue("customer.space.separator"));

        for (WorkspaceDto space : availableSpaces) {
            System.out.printf(getValue("customer.space.format"),
                    space.getId(), space.getType().getDisplayName(), space.getPrice());
        }
        System.out.println(getValue("customer.space.separator"));
    }

    public void printReservationEndDateMessage() {
        System.out.print(getValue("customer.enter.end.date"));
    }

    public void printGetIdMessage() {
        System.out.print(getValue("common.enter.space.id"));
    }

    public void printCustomerReservations(List<ReservationDto> userReservations) {
        System.out.println(getValue("customer.view.reservations"));

        System.out.println(getValue("customer.reservation.separator"));
        String tableHeaderFormat = getValue("customer.reservation.format.table");
        System.out.printf(tableHeaderFormat, "Reservation ID", "Space ID", "Type", "Price", "Booking Start", "Booking End");
        System.out.println(getValue("customer.reservation.separator"));

        for (ReservationDto reservation : userReservations) {
            WorkspaceDto space = reservation.getSpace();
            System.out.printf(getValue("customer.reservation.format"),
                    reservation.getId(), space.getId(), space.getType().getDisplayName(), space.getPrice(),
                    formatDateTime(reservation.getStartTime()), formatDateTime(reservation.getEndTime()));
        }
        System.out.println(getValue("customer.reservation.separator"));
    }

    public void printErrorRemoveReservationMessage(long id) {
        System.out.printf(getValue("customer.error.remove.reservation"), id);
    }

    public void printEmptyReservationMessage() {
        System.out.println(getValue("customer.error.reservations"));
    }

    public void printErrorReservationExistMessage() {
        System.out.println(getValue("customer.error.reservation.exists"));
    }

    public void printBrowseSpacesItem() {
        System.out.println(getValue("customer.browse.spaces"));
    }

    public void printMakeReservationItem() {
        System.out.println(getValue("customer.make.reservation"));
    }

    public void printViewReservationItem() {
        System.out.println(getValue("customer.view.reservations.menu"));
    }

    public void printCancelReservationItem() {
        System.out.println(getValue("customer.cancel.reservation"));
    }

    public void printErrorAddReservationMessage() {
        System.out.println(getValue("customer.error.already.booked"));
    }

    public void printErrorNoAvailableSpacesMessage() {
        System.out.println(getValue("customer.error.available.spaces"));
    }

    public void printIdReservationMessage() {
        System.out.println(getValue("customer.enter.reservation.id"));
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getValue("common.date.format"));
        return dateTime.format(formatter);
    }
}

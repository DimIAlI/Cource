package org.example.view;

import org.example.model.dto.space.ReservationDto;
import org.example.model.dto.space.WorkspaceDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class CustomerView {

    private final Map<String, String> customerMessages;

    public CustomerView(@Qualifier("customerMessages") Map<String, String> customerMessages) {
        this.customerMessages = customerMessages;
    }

    public void printWelcomeMessage() {
        System.out.println(customerMessages.get("welcome"));
        System.out.println(customerMessages.get("loginRules"));
        System.out.print(customerMessages.get("enterUsername"));
    }

    public void printMenu() {
        System.out.println(customerMessages.get("customerMenu"));
        System.out.println(customerMessages.get("menuOption1"));
        System.out.println(customerMessages.get("menuOption2"));
        System.out.println(customerMessages.get("menuOption3"));
        System.out.println(customerMessages.get("menuOption4"));
        System.out.println(customerMessages.get("menuOption5"));

        System.out.print(customerMessages.get("enterChoice"));
    }

    public void printReservationStartDateMessage() {
        System.out.print(customerMessages.get("enterStartDate"));
    }

    public void printAvailableSpaces(List<WorkspaceDto> availableSpaces) {
        System.out.println(customerMessages.get("availableSpaces"));

        System.out.println(customerMessages.get("spaceSeparator"));
        String tableHeaderFormat = customerMessages.get("spaceFormatTable");
        System.out.printf(tableHeaderFormat, "ID", "Type", "Price");
        System.out.println(customerMessages.get("spaceSeparator"));

        for (WorkspaceDto space : availableSpaces) {
            System.out.printf(customerMessages.get("spaceFormat"),
                    space.getId(), space.getType().getDisplayName(), space.getPrice());
        }
        System.out.println(customerMessages.get("spaceSeparator"));
    }

    public void printReservationEndDateMessage() {
        System.out.print(customerMessages.get("enterEndDate"));
    }

    public void printGetIdMessage() {
        System.out.print(customerMessages.get("enterSpaceId"));
    }

    public void printCustomerReservations(List<ReservationDto> userReservations) {
        System.out.println(customerMessages.get("viewReservations"));

        System.out.println(customerMessages.get("reservationSeparator"));
        String tableHeaderFormat = customerMessages.get("reservationFormatTable");
        System.out.printf(tableHeaderFormat, "Reservation ID", "Space ID", "Type", "Price", "Booking Start", "Booking End");
        System.out.println(customerMessages.get("reservationSeparator"));

        for (ReservationDto reservation : userReservations) {
            WorkspaceDto space = reservation.getSpace();
            System.out.printf(customerMessages.get("reservationFormat"),
                    reservation.getId(), space.getId(), space.getType().getDisplayName(), space.getPrice(),
                    formatDateTime(reservation.getStartTime()), formatDateTime(reservation.getEndTime()));
        }
        System.out.println(customerMessages.get("reservationSeparator"));
    }

    public void printErrorRemoveReservationMessage(long id) {
        System.out.printf(customerMessages.get("errorRemoveReservation"), id);
    }

    public void printEmptyReservationMessage() {
        System.out.println(customerMessages.get("errorReservations"));
    }

    public void printErrorReservationExistMessage() {
        System.out.println(customerMessages.get("errorReservationExists"));
    }

    public void printBrowseSpacesItem() {
        System.out.println(customerMessages.get("browseSpaces"));
    }

    public void printMakeReservationItem() {
        System.out.println(customerMessages.get("makeReservation"));
    }

    public void printViewReservationItem() {
        System.out.println(customerMessages.get("viewReservationsMenu"));
    }

    public void printCancelReservationItem() {
        System.out.println(customerMessages.get("cancelReservation"));
    }

    public void printErrorAddReservationMessage() {
        System.out.println(customerMessages.get("errorAlreadyBooked"));
    }

    public void printErrorNoAvailableSpacesMessage() {
        System.out.println(customerMessages.get("errorAvailableSpaces"));
    }

    public void printIdReservationMessage() {
        System.out.println(customerMessages.get("enterReservationId"));
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(customerMessages.get("dateFormat"));
        return dateTime.format(formatter);
    }
}

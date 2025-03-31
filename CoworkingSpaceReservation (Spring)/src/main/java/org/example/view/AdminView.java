package org.example.view;

import org.example.model.dto.account.CustomerDto;
import org.example.model.dto.space.ReservationDto;
import org.example.model.dto.space.WorkspaceDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Component
public class AdminView {
    private final Map<String, String> adminMessages;

    public AdminView(@Qualifier("adminMessages")Map<String, String> adminMessages) {
        this.adminMessages = adminMessages;
    }

    public void printWelcomeMessage() {
        System.out.println(adminMessages.get("welcome"));
        System.out.println(adminMessages.get("loginRules"));
        System.out.print(adminMessages.get("enterUsername"));
    }

    public void printMenu() {
        System.out.println(adminMessages.get("adminMenu"));
        System.out.println(adminMessages.get("menuOption1"));
        System.out.println(adminMessages.get("menuOption2"));
        System.out.println(adminMessages.get("menuOption3"));
        System.out.println(adminMessages.get("menuOption4"));

        System.out.print(adminMessages.get("enterChoice"));
    }

    public void printAllSpaces(List<WorkspaceDto> allSpaces) {
        System.out.println(adminMessages.get("spaceSeparator"));
        String tableHeaderFormat = adminMessages.get("spaceFormatTable");
        System.out.printf(tableHeaderFormat, "ID", "Type", "Price", "Available");
        System.out.println(adminMessages.get("spaceSeparator"));

        for (WorkspaceDto space : allSpaces) {
            System.out.printf(adminMessages.get("spaceFormat"),
                    space.getId(), space.getType().getDisplayName(), space.getPrice(), space.getAvailable() ? "Yes" : "No");
        }
        System.out.println(adminMessages.get("spaceSeparator"));
    }

    public void printAddType() {
        System.out.print(adminMessages.get("enterSpaceType"));
    }

    public void printSuccessMessage() {
        System.out.print(adminMessages.get("successOperation"));
    }

    public void printIdMessage() {
        System.out.print(adminMessages.get("enterSpaceId"));
    }

    public void printAllReservations(List<ReservationDto> allReservations) {
        System.out.println(adminMessages.get("listReservations"));

        System.out.println(adminMessages.get("reservationSeparator"));

        String tableHeaderFormat = adminMessages.get("reservationFormatTable");
        System.out.printf(tableHeaderFormat, "Reservation ID", "User Login", "Space ID", "Type", "Price", "Booking Start", "Booking End");
        System.out.println(adminMessages.get("reservationSeparator"));

        for (ReservationDto reservation : allReservations) {
            WorkspaceDto space = reservation.getSpace();
            CustomerDto customer = reservation.getCustomer();

            System.out.printf(adminMessages.get("reservationFormat"),
                    reservation.getId(), customer.getLogin(), space.getId(), space.getType().getDisplayName(), space.getPrice(),
                    formatDateTime(reservation.getStartTime()), formatDateTime(reservation.getEndTime()));
        }
        System.out.println(adminMessages.get("reservationSeparator"));
    }

    public void printEmptySpaceMessage() {
        System.out.println(adminMessages.get("errorSpaces"));
    }

    public void printAllSpacesMessage() {
        System.out.println(adminMessages.get("listSpaces"));
    }

    public void printAddPrice() {
        System.out.println(adminMessages.get("enterPrice"));
    }

    public void printErrorAddMessage(String price, String type) {
        System.out.printf(adminMessages.get("errorSpaceExists"), type, price);
    }

    public void printEmptyReservationMessage() {
        System.out.println(adminMessages.get("errorReservations"));
    }

    public void printAddSpaceMenuItem() {
        System.out.println(adminMessages.get("addSpace"));
    }

    public void printRemoveSpaceMenuItem() {
        System.out.println(adminMessages.get("removeSpace"));
    }

    public void printViewReservationMenuItem() {
        System.out.println(adminMessages.get("viewReservations"));
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(adminMessages.get("dateFormat"));
        return dateTime.format(formatter);
    }
}

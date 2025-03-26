package org.example.view;

import org.example.model.dto.CustomerDto;
import org.example.model.dto.ReservationDto;
import org.example.model.dto.WorkspaceDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.example.util.PropertiesUtil.*;

public class AdminView {

    public void printWelcomeMessage() {
        System.out.println(getValue("admin.welcome"));
        System.out.println(getValue("common.login.rules"));
        System.out.print(getValue("admin.enter.username"));
    }

    public void printMenu() {
        System.out.println(getValue("admin.menu"));
        System.out.println(getValue("admin.menu.option1"));
        System.out.println(getValue("admin.menu.option2"));
        System.out.println(getValue("admin.menu.option3"));
        System.out.println(getValue("admin.menu.option4"));

        System.out.print(getValue("common.enter.choice"));
    }

    public void printAllSpaces(List<WorkspaceDto> allSpaces) {
        System.out.println(getValue("admin.space.separator"));
        System.out.printf(getValue("admin.space.format.table"));
        System.out.println(getValue("admin.space.separator"));

        for (WorkspaceDto space : allSpaces) {
            System.out.printf(getValue("admin.space.format"),
                    space.getId(), space.getType().getDisplayName(), space.getPrice(), space.isAvailable() ? "Yes" : "No");
        }
        System.out.println(getValue("admin.space.separator"));
    }

    public void printAddType() {
        System.out.print(getValue("admin.enter.space.type"));
    }

    public void printSuccessMessage() {
        System.out.print(getValue("common.success.operation"));
    }

    public void printIdMessage() {
        System.out.print(getValue("common.enter.space.id"));
    }

    public void printAllReservations(List<ReservationDto> allReservations) {
        System.out.println(getValue("admin.list.reservations"));

        System.out.println(getValue("admin.reservation.separator"));
        System.out.printf(getValue("admin.reservation.format.table"));
        System.out.println(getValue("admin.reservation.separator"));

        for (ReservationDto reservation : allReservations) {
            WorkspaceDto space = reservation.getSpace();
            CustomerDto customer = reservation.getCustomer();

            System.out.printf(getValue("admin.reservation.format"),
                    reservation.getId(), customer.getLogin(), space.getId(), space.getType().getDisplayName(), space.getPrice(),
                    formatDateTime(reservation.getStartTime()), formatDateTime(reservation.getEndTime()));
        }
        System.out.println(getValue("admin.reservation.separator"));
    }

    public void printEmptySpaceMessage() {
        System.out.println(getValue("admin.error.spaces"));
    }

    public void printAllSpacesMessage() {
        System.out.println(getValue("admin.list.spaces"));
    }

    public void printAddPrice() {
        System.out.println(getValue("admin.enter.price"));
    }

    public void printErrorAddMessage(String price, String type) {
        System.out.printf(getValue("admin.error.space.exists"), type, price);
    }

    public void printEmptyReservationMessage() {
        System.out.println(getValue("admin.error.reservations"));
    }

    public void printAddSpaceMenuItem() {
        System.out.println(getValue("admin.add.space"));
    }

    public void printRemoveSpaceMenuItem() {
        System.out.println(getValue("admin.remove.space"));
    }

    public void printViewReservationMenuItem() {
        System.out.println(getValue("admin.view.reservations"));
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getValue("common.date.format"));
        return dateTime.format(formatter);
    }
}

package org.example.view;

import org.example.model.dto.ReservationDto;
import org.example.model.dto.WorkspaceDto;

import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomerView {

    public void printWelcomeMessage() {
        System.out.println("\n=User Login=");
        System.out.println("(The expected login must be longer than 5 characters, not contain special characters, and be in English)\n");
        System.out.print("Enter your username: ");
    }

    public void printMenu() {
        System.out.println("\nCustomer Menu:");
        System.out.println("1. Browse available spaces");
        System.out.println("2. Make a reservation");
        System.out.println("3. View my reservations");
        System.out.println("4. Cancel a reservation");
        System.out.println("5. Logout");

        System.out.print("\nEnter your choice: ");

    }

    public void printReservationStartDateMessage() {
        System.out.print("\nEnter the start date and time of the booking (format: dd-MM-yyyy HH:mm)");
    }

    public void printAvailableSpaces(List<WorkspaceDto> availableSpaces) {
        System.out.println("\nThe List of all available spaces:");

        System.out.println("----------------------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-30s |%n", "ID", "Type", "Price");
        System.out.println("----------------------------------------------------------------------");

        for (WorkspaceDto space : availableSpaces) {
            System.out.printf("| %-10d | %-20s | %-30.2f |%n",
                    space.getId(), space.getType().getDisplayName(), space.getPrice());
        }
        System.out.println("----------------------------------------------------------------------");
    }

    public void printReservationEndDateMessage() {
        System.out.print("\nEnter the end date and time of the booking (format: dd-MM-yyyy HH:mm)");
    }

    public void printGetIdMessage() {
        System.out.print("\nEnter ID");
    }

    public void printCustomerReservations(List<ReservationDto> userReservations) {
        System.out.println("The list of your reservations:");

        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-15s | %-10s | %-20s | %-30s | %-20s | %-20s |%n",
                "Reservation ID", "Space ID", "Type", "Price", "Booking Start", "Booking End");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");

        for (ReservationDto reservation : userReservations) {
            WorkspaceDto space = reservation.getSpace();
            System.out.printf("| %-15d | %-10d | %-20s | %-30.2f | %-20s | %-20s |%n",
                    reservation.getId(), space.getId(), space.getType().getDisplayName(), space.getPrice(),
                    formatDateTime(reservation.getStartTime()), formatDateTime(reservation.getEndTime()));
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
    }

    public void printErrorRemoveReservationMessage(long id) {
        System.out.printf("\nReservation with ID %s cannot be deleted", id);
    }

    public void printEmptyReservationMessage() {
        System.out.println("You have no reservations.");
    }

    public void printErrorReservationExistMessage() {
        System.out.println("\nReservation with these details already exists");
    }

    public void printBrowseSpacesItem() {
        System.out.println("\n=Browse available spaces=");
    }

    public void printMakeReservationItem() {
        System.out.println("\n=Make a reservation=");
    }

    public void printViewReservationItem() {
        System.out.println("\n=View my reservations=");
    }

    public void printCancelReservationItem() {
        System.out.println("\n=Cancel a reservation=");
    }

    public void printErrorAddReservationMessage(){
        System.out.println("\nThe workspace is already booked for this time!");
    }
    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
        return dateTime.format(formatter);
    }

    public void printErrorNoAvailableSpacesMessage() {
        System.out.println("\nNo available spaces during this period!");
    }

    public void printIdReservationMessage() {
        System.out.println("\nEnter the reservation ID");
    }
}

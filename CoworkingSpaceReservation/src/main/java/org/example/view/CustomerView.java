package org.example.view;

import org.example.model.Reservation;
import org.example.model.Workspace;

import java.util.List;

public class CustomerView {

    public void printWelcomeMessage() {
        System.out.println("\nUser Login");
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

    public void printAvailableSpaces(List<Workspace> availableSpaces) {
        System.out.println("\n" + availableSpaces);
    }

    public void printReservationEndDateMessage() {
        System.out.print("\nEnter the end date and time of the booking (format: dd-MM-yyyy HH:mm)");
    }

    public void printGetIdMessage() {
        System.out.print("Enter ID");
    }

    public void printCustomerReservations(List<Reservation> userReservations) {
        System.out.println("The list of your reservations:");
        System.out.println(userReservations);
    }

    public void printErrorRemoveReservationMessage(long id) {
        System.out.printf("\nReservation with ID %s cannot be deleted", id);
    }

    public void printEmptyReservationMessage() {
        System.out.println("You have no reservations.");
    }

    public void printErrorReservationExistMessage() {
        System.out.println("\nThe workspace is already booked for this time!");
    }
}

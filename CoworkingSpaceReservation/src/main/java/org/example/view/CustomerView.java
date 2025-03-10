package org.example.view;

import org.example.model.Reservation;
import org.example.model.Workspace;

import java.util.List;

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

    public void printAvailableSpaces(List<Workspace> availableSpaces) {
        System.out.println("\nThe List of all available spaces:");
        System.out.println(availableSpaces);
    }

    public void printReservationEndDateMessage() {
        System.out.print("\nEnter the end date and time of the booking (format: dd-MM-yyyy HH:mm)");
    }

    public void printGetIdMessage() {
        System.out.print("\nEnter ID");
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

    public void printBrowseSpacesItem() {
        System.out.println("\n=Browse available spaces=");
    }
    public void printMakeReservationItem(){
        System.out.println("\n=Make a reservation=");
    }
    public void printViewReservationItem(){
        System.out.println("\n=View my reservations=");
    }
    public void printCancelReservationItem(){
        System.out.println("\n=Cancel a reservation=");
    }

}

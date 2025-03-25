package org.example.view;

import org.example.model.dto.CustomerDto;
import org.example.model.dto.ReservationDto;
import org.example.model.dto.WorkspaceDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdminView {

    public void printWelcomeMessage() {
        System.out.println("\n=Admin Login=");
        System.out.println("(The expected login must be longer than 5 characters, not contain special characters, and be in English)\n");
        System.out.print("Enter Admin username: ");
    }

    public void printMenu() {
        System.out.println("\nAdmin Menu:");
        System.out.println("1. Add a new coworking space");
        System.out.println("2. Remove a coworking space");
        System.out.println("3. View all reservations");
        System.out.println("4. Logout");

        System.out.print("\nEnter your choice: ");
    }

    public void printAllSpaces(List<WorkspaceDto> allSpaces) {
        System.out.println("------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-30s | %-9s |%n", "ID", "Type", "Price", "Available");
        System.out.println("------------------------------------------------------------------------------------");

        for (WorkspaceDto space : allSpaces) {
            System.out.printf("| %-10d | %-20s | %-30.2f | %-9s |%n",
                    space.getId(), space.getType().getDisplayName(), space.getPrice(), space.isAvailable() ? "Yes" : "No");
        }
        System.out.println("------------------------------------------------------------------------------------");
    }

    public void printAddType() {
        System.out.print("\nEnter the type of space from the options provided " +
                "(Open Space, Dedicated Desk, Private Office, Meeting Room, Event Space, Hot Desk, Shared Office)");
    }

    public void printSuccessMessage() {
        System.out.print("\nOperation completed");
    }

    public void printIdMessage() {
        System.out.print("\nEnter the space ID");
    }

    public void printAllReservations(List<ReservationDto> allReservations) {
        System.out.println("The list of all reservations:");

        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-15s | %-35s | %-15s | %-20s | %-30s | %-20s | %-20s |%n",
                "Reservation ID", "User Login", "Space ID", "Type", "Price", "Booking Start", "Booking End");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (ReservationDto reservation : allReservations) {
            WorkspaceDto space = reservation.getSpace();
            CustomerDto customer = reservation.getCustomer();

            System.out.printf("| %-15d | %-35s | %-15d | %-20s | %-30.2f | %-20s | %-20s |%n",
                    reservation.getId(), customer.getLogin(), space.getId(), space.getType().getDisplayName(), space.getPrice(),
                    formatDateTime(reservation.getStartTime()), formatDateTime(reservation.getEndTime()));
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public void printEmptySpaceMessage() {
        System.out.println("No spaces have been added yet!");
    }

    public void printAllSpacesMessage() {
        System.out.println("\nList of all spaces: ");
    }

    public void printAddPrice() {
        System.out.println("\nEnter the price (as an integer or a decimal number, with a dot as the separator between the integer and fractional parts,");
        System.out.print("and the value must be greater than 0)");
    }

    public void printErrorAddMessage(String price, String type) {
        System.out.printf("""
                          \nThe space with type %s and price %s already exists!
                          """, type, price);
    }

    public void printEmptyReservationMessage() {
        System.out.println("System has no reservations");
    }

    public void printAddSpaceMenuItem() {
        System.out.println("\n=Add a new coworking space=");
    }

    public void printRemoveSpaceMenuItem(){
        System.out.println("\n=Remove a coworking space=");
    }

    public void printViewReservationMenuItem(){
        System.out.println("\n=View all reservations=");
    }
    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
        return dateTime.format(formatter);
    }
}

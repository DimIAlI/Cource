package org.example.view;

import org.example.model.Reservation;
import org.example.model.Workspace;

import java.util.List;
import java.util.Map;

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

    public void printAllSpaces(List<Workspace> allSpaces) {
        System.out.println(allSpaces);

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

    public void printAllReservations(Map<Long, Reservation> allReservations) {
        System.out.println("The list of all reservations:");
        System.out.println(allReservations);
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
}

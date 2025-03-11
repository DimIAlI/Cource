package org.example.view;

public class GeneralView {
    public void printWelcomeMessage() {
        System.out.println("\n=Welcome to the Coworking Space Reservation System!=");
    }

    public void printMenu() {
        System.out.println("\nPlease select an option:");
        System.out.println("1. Admin Login");
        System.out.println("2. User Login");
        System.out.println("3. Exit");
    }

    public void printEnterChoiceMessage() {
        System.out.print("\nEnter your choice: ");
    }

    public void printErrorMessage() {
        System.out.print("\nError value, try to choose another value!\n");
    }

    public void printExitMessage() {
        System.out.println("\nThank you for using the Coworking Space Reservation System! Goodbye!");
    }

    public void printPressAnySymbolMessage() {
        System.out.print("\nPress any key to exit:");
    }

    public void printErrorLoginMessage() {
        System.out.println("\nYour login does not meet the validation requirements!");
    }

    public void printErrorIdMessage(Long id) {
        System.out.printf("\nId %s is not valid!%n", id);
    }
}

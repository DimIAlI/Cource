package org.example.controller;

import org.example.model.*;
import org.example.view.GeneralView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;


public class GeneralController {
    private final Scanner scanner;
    private final GeneralView generalView;
    private final AdminController adminController;
    private final CustomerController customerController;
    private final WorkspaceController workspaceController;
    private final ReservationController reservationController;

    private final ApplicationStateController applicationStateController;

    public GeneralController(Scanner scanner, AdminController adminController, CustomerController customerController, WorkspaceController workspaceController, ReservationController reservationController, GeneralView generalView, ApplicationStateController applicationStateController) {
        this.scanner = scanner;
        this.adminController = adminController;
        this.customerController = customerController;
        this.workspaceController = workspaceController;
        this.reservationController = reservationController;
        this.generalView = generalView;
        this.applicationStateController = applicationStateController;
    }

    public static GeneralController createGeneralController() {
        Scanner scanner = new Scanner(System.in);
        GeneralView generalView = new GeneralView();
        AdminController adminController = AdminController.createAdminController();
        CustomerController customerController = CustomerController.createCustomerController();
        WorkspaceController workspaceController = WorkspaceController.createWorkspaceController();
        ReservationController reservationController = ReservationController.createReservationController();
        ApplicationStateController applicationStateController = new ApplicationStateController();
        return new GeneralController(scanner, adminController, customerController, workspaceController, reservationController, generalView, applicationStateController);
    }

    public void showWelcomeMessage() {
        generalView.printWelcomeMessage();
    }

    public void showErrorMessage() {
        generalView.printErrorMessage();
    }

    public void showExitMessage() {
        generalView.printExitMessage();
    }

    public void showWelcomeMessage(User currentUser) {
        if (currentUser instanceof Admin) adminController.showWelcomeMessage();
        else customerController.showWelcomeMessage();
    }

    public void showMenu(User currentUser) {
        if (currentUser instanceof Admin) adminController.showMenu();
        else customerController.showMenu();
    }

    public void showAddType() {

        adminController.showAddType();

    }

    public void showAllReservations() {
        Optional<Map<Long, Reservation>> allReservations = Optional.ofNullable(reservationController.getAllReservations());
        adminController.showAllReservations(allReservations);
    }

    public void showAvailableSpaces(LocalDateTime startTime, LocalDateTime endTime) {
        List<Workspace> availableSpaces = workspaceController.getAvailableSpaces(startTime, endTime);
        customerController.showAvailableSpaces(availableSpaces);

    }

    public void addReservation(User currentUser, long id, LocalDateTime startTime, LocalDateTime endTime) {
        Workspace workspace = workspaceController.getWorkspace(id);
        reservationController.addReservation(currentUser, workspace, startTime, endTime);
    }

    public void showViewCustomerReservations(User currentUser) {
        Optional<List<Reservation>> userReservations = reservationController.getUserReservations(currentUser);
        customerController.showUserReservations(userReservations);
    }

    public void cancelReservation(long id, User currentUser) {
        reservationController.cancelReservation(id, currentUser);

    }

    public void showSuccessMessage() {
        adminController.showSuccessMessage();
    }

    public void showPressAnySymbolMessage() {
        generalView.printPressAnySymbolMessage();
    }

    public void showReservationStartDateMessage() {
        customerController.showReservationStartDateMessage();
    }

    public void showReservationEndDateMessage() {
        customerController.showReservationEndDateMessage();
    }

    public void showGetIdMessage() {
        customerController.showGetIdMessage();
    }

    public void showEnterChoiceMessage() {
        generalView.printEnterChoiceMessage();
    }

    public void addWorkspace(SpaceType type, double price) {
        workspaceController.addWorkspace(type, price);
    }

    public void removeWorkspace(long id) {
        workspaceController.removeWorkspace(id);
    }

    public String getUserMessage() {
        return scanner.nextLine().trim();
    }

    public User getAdmin(User user, String choice) {
        return adminController.getAdmin(user, choice);
    }

    public User getCustomer(User user, String choice) {
        return customerController.getCustomer(user, choice);
    }

    public User getEmptyAdmin() {
        return adminController.getEmptyAdmin();
    }

    public User getEmptyCustomer() {
        return customerController.getEmptyCustomer();
    }

    public void showErrorLoginMessage() {
        generalView.printErrorLoginMessage();
    }

    public boolean showAllSpacesMessage() {
        adminController.showAllSpacesMessage();
        List<Workspace> allSpaces = workspaceController.getAllSpaces();

        if (allSpaces.isEmpty()) {
            adminController.showEmptySpaceMessage();
            return false;
        } else {
            adminController.showAllSpaces(allSpaces);
            return true;
        }
    }

    public void showAddPrice() {
        adminController.showAddPrice();
    }

    public void showErrorAddMessage(String price, String type) {
        adminController.showErrorAddMessage(price, type);
    }

    public void showIdMessage() {
        adminController.showIdMessage();
    }

    public void showErrorIdMessage(Long id) {
        generalView.printErrorIdMessage(id);
    }

    public void showErrorRemoveReservationMessage(long id) {
        customerController.showErrorRemoveReservationMessage(id);
    }

    public void showErrorReservationExistMessage() {
        customerController.showErrorReservationExistMessage();
    }

    public void saveChanges() {
        applicationStateController.saveChanges();
    }
}

package org.example.controller;

import org.example.model.entity.*;
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

    public GeneralController(Scanner scanner,
                             AdminController adminController,
                             CustomerController customerController,
                             WorkspaceController workspaceController,
                             ReservationController reservationController,
                             GeneralView generalView) {

        this.scanner = scanner;
        this.adminController = adminController;
        this.customerController = customerController;
        this.workspaceController = workspaceController;
        this.reservationController = reservationController;
        this.generalView = generalView;
    }

    public static GeneralController createGeneralController() {
        Scanner scanner = new Scanner(System.in);
        GeneralView generalView = new GeneralView();
        AdminController adminController = AdminController.createAdminController();
        CustomerController customerController = CustomerController.createCustomerController();
        WorkspaceController workspaceController = WorkspaceController.createWorkspaceController();
        ReservationController reservationController = ReservationController.createReservationController();
        return new GeneralController(scanner, adminController, customerController, workspaceController, reservationController, generalView);
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

    public void showWelcomeMessage(UserEntity currentUser) {

        if (currentUser instanceof AdminEntity) adminController.showWelcomeMessage();
        else customerController.showWelcomeMessage();
    }

    public void showMenu() {
        generalView.printMenu();
    }

    public void showMenu(UserEntity currentUser) {
        if (currentUser instanceof AdminEntity) adminController.showMenu();
        else customerController.showMenu();
    }

    public void showAddType() {
        adminController.showAddType();
    }

    public void showAllReservations() {
        Optional<Map<Long, ReservationEntity>> allReservations = Optional.ofNullable(reservationController.getAllReservations());
        adminController.showAllReservations(allReservations);
    }

    public void showAvailableSpaces(LocalDateTime startTime, LocalDateTime endTime) {
        List<WorkspaceEntity> availableSpaces = workspaceController.getAvailableSpaces(startTime, endTime);
        customerController.showAvailableSpaces(availableSpaces);
    }

    public void showViewCustomerReservations(UserEntity currentUser) {
        Optional<List<ReservationEntity>> userReservations = reservationController.getUserReservations(currentUser);
        customerController.showUserReservations(userReservations);
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

    public void showEnterChoiceMessage() {
        generalView.printEnterChoiceMessage();
    }

    public void showGetIdMessage() {
        customerController.showGetIdMessage();
    }

    public void showErrorLoginMessage() {
        generalView.printErrorLoginMessage();
    }

    public boolean showAllSpacesMessage() {
        adminController.showAllSpacesMessage();
        List<WorkspaceEntity> allSpaces = workspaceController.getAllSpaces();

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

    public void cancelReservation(long id, UserEntity currentUser) {
        reservationController.cancelReservation(id, currentUser);
    }

    public void addReservation(UserEntity currentUser, long id, LocalDateTime startTime, LocalDateTime endTime) {
        WorkspaceEntity workspace = workspaceController.getWorkspace(id);
        reservationController.addReservation(currentUser, workspace, startTime, endTime);
    }

    public void addWorkspace(SpaceTypeEntity type, double price) {
        workspaceController.addWorkspace(type, price);
    }

    public void removeWorkspace(long id) {
        workspaceController.removeWorkspace(id);
    }

    public String getUserMessage() {
        return scanner.nextLine().trim();
    }

    public UserEntity getUser(UserEntity user, String login) {
        if (user instanceof AdminEntity) {
            return getAdmin(user, login);
        }
        return getCustomer(user, login);
    }

    private UserDto getAdmin(UserDto user, String login) {
        return adminController.getAdmin(user, login);
    }

    private UserEntity getCustomer(UserEntity user, String choice) {
        return customerController.getCustomer(user, choice);
    }

    public void showAddSpaceMenuItem() {
        adminController.showAddSpaceMenuItem();
    }

    public void showRemoveSpaceMenuItem() {
        adminController.showRemoveSpaceMenuItem();
    }

    public void showViewReservationMenuItem() {
        adminController.showViewReservationMenuItem();
    }

    public void showBrowseSpacesItem() {
        customerController.showBrowseSpacesItem();
    }

    public void showMakeReservationItem() {
        customerController.printMakeReservationItem();
    }

    public void showViewReservationItem() {
        customerController.showViewReservationItem();
    }

    public void showCancelReservationItem() {
        customerController.showCancelReservationItem();
    }
}

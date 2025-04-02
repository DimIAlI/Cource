package org.example.controller;

import org.example.model.dto.account.AdminDto;
import org.example.model.dto.account.UserDto;
import org.example.model.dto.space.ReservationDto;
import org.example.model.dto.space.SpaceTypeDto;
import org.example.model.dto.space.WorkspaceDto;
import org.example.view.GeneralView;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

@Controller
public class GeneralController {
    private final Scanner scanner;
    private final GeneralView generalView;
    private final AdminController adminController;
    private final CustomerController customerController;
    private final WorkspaceController workspaceController;
    private final ReservationController reservationController;

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

    public void showWelcomeMessage() {
        generalView.printWelcomeMessage();
    }

    public void showErrorMessage() {
        generalView.printErrorMessage();
    }

    public void showExitMessage() {
        generalView.printExitMessage();
    }

    public void showWelcomeMessage(UserDto currentUser) {

        if (currentUser instanceof AdminDto) adminController.showWelcomeMessage();
        else customerController.showWelcomeMessage();
    }

    public void showMenu() {
        generalView.printMenu();
    }

    public void showMenu(UserDto currentUser) {
        if (currentUser instanceof AdminDto) adminController.showMenu();
        else customerController.showMenu();
    }

    public void showAddType() {
        adminController.showAddType();
    }

    public void showAllReservations() {
        List<ReservationDto> allReservations = reservationController.getAllReservations();
        adminController.showAllReservations(allReservations);
    }

    public void showAvailableSpaces(LocalDateTime startTime, LocalDateTime endTime) {
        List<WorkspaceDto> availableSpaces = workspaceController.getAvailableSpaces(startTime, endTime);
        customerController.showAvailableSpaces(availableSpaces);
    }

    public void showViewCustomerReservations(UserDto currentUser) {
        List<ReservationDto> userReservations = reservationController.getUserReservations(currentUser);
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
        List<WorkspaceDto> allSpaces = workspaceController.getAllSpaces();

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

    public void cancelReservation(long id, UserDto currentUser) {
        reservationController.cancelReservation(id, currentUser);
    }

    public void addReservation(UserDto currentUser, long id, LocalDateTime startTime, LocalDateTime endTime) {
        WorkspaceDto workspace = workspaceController.getWorkspace(id);
        reservationController.addReservation(currentUser, workspace, startTime, endTime);
    }

    public void addWorkspace(SpaceTypeDto type, double price) {
        workspaceController.addWorkspace(type, price);
    }

    public void removeWorkspace(long id) {
        workspaceController.removeWorkspace(id);
    }

    public String getUserMessage() {
        return scanner.nextLine().trim();
    }

    public UserDto getUser(UserDto user, String login) {
        if (user instanceof AdminDto) {
            return getAdmin(user, login);
        }
        return getCustomer(user, login);
    }

    private UserDto getAdmin(UserDto user, String login) {
        return adminController.getAdmin(user, login);
    }

    private UserDto getCustomer(UserDto user, String choice) {
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

    public void showErrorAddReservationMessage() {
        customerController.showErrorAddReservationMessage();
    }

    public void showErrorNoAvailableSpacesMessage() {
        customerController.showErrorNoAvailableSpacesMessage();
    }

    public void showIdReservationMessage() {
        customerController.showIdReservationMessage();
    }
}

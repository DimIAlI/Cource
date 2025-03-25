package org.example.controller;

import org.example.model.dto.ReservationDto;
import org.example.model.dto.UserDto;
import org.example.model.dto.WorkspaceDto;
import org.example.model.service.AdminManager;
import org.example.view.AdminView;

import java.util.List;

class AdminController {

    private final AdminView adminView;

    AdminController(AdminView adminView) {
        this.adminView = adminView;
    }

    static AdminController createAdminController() {
        AdminView adminView = new AdminView();
        return new AdminController(adminView);
    }

    void showWelcomeMessage() {
        adminView.printWelcomeMessage();
    }

    void showMenu() {
        adminView.printMenu();
    }

    void showAllSpaces(List<WorkspaceDto> allSpaces) {
        adminView.printAllSpaces(allSpaces);
    }

    void showAddType() {
        adminView.printAddType();
    }

    void showSuccessMessage() {
        adminView.printSuccessMessage();
    }

    void showIdMessage() {
        adminView.printIdMessage();
    }

    void showAllReservations(List<ReservationDto> allReservations) {
        if (!allReservations.isEmpty()) {
            adminView.printAllReservations(allReservations);

        } else adminView.printEmptyReservationMessage();
    }

    void showEmptySpaceMessage() {
        adminView.printEmptySpaceMessage();
    }

    void showAllSpacesMessage() {
        adminView.printAllSpacesMessage();
    }

    void showAddPrice() {
        adminView.printAddPrice();
    }

    void showErrorAddMessage(String price, String type) {
        adminView.printErrorAddMessage(price, type);
    }

    UserDto getAdmin(UserDto user, String login) {
        return AdminManager.getInstance().getAdmin(user, login);
    }

    void showAddSpaceMenuItem() {
        adminView.printAddSpaceMenuItem();
    }

    void showRemoveSpaceMenuItem() {
        adminView.printRemoveSpaceMenuItem();
    }

    void showViewReservationMenuItem() {
        adminView.printViewReservationMenuItem();
    }
}

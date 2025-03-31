package org.example.controller;

import org.example.model.dto.space.ReservationDto;
import org.example.model.dto.account.UserDto;
import org.example.model.dto.space.WorkspaceDto;
import org.example.model.service.AdminManager;
import org.example.view.AdminView;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
class AdminController {

    private final AdminView adminView;
    private final AdminManager adminManager;

    AdminController(AdminView adminView, AdminManager adminManager) {
        this.adminView = adminView;
        this.adminManager = adminManager;
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
        return adminManager.getAdmin(user, login);
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

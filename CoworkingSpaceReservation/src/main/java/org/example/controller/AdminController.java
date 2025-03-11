package org.example.controller;

import org.example.model.*;
import org.example.view.AdminView;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    void showAllSpaces(List<Workspace> allSpaces) {
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

    void showAllReservations(Optional<Map<Long, Reservation>> allReservations) {
        allReservations.ifPresentOrElse(adminView::printAllReservations,
                adminView::printEmptyReservationMessage);
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

    User getAdmin(User user, String login) {
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

package org.example.controller;

import org.example.model.*;
import org.example.view.AdminView;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AdminController {

    private final AdminView adminView;

    public AdminController(AdminView adminView) {
        this.adminView = adminView;
    }

    static AdminController createAdminController() {
        AdminView adminView = new AdminView();
        return new AdminController(adminView);
    }

    public void showWelcomeMessage() {
        adminView.printWelcomeMessage();
    }

    public void showMenu() {
        adminView.printMenu();
    }


    public void showAllSpaces(List<Workspace> allSpaces) {

        adminView.printAllSpaces(allSpaces);
    }

    public void showAddType() {
        adminView.printAddType();
    }

    public void showSuccessMessage() {
        adminView.printSuccessMessage();
    }

    public void showIdMessage() {
        adminView.printIdMessage();
    }

    public void showAllReservations(Optional<Map<Long, Reservation>> allReservations) {

        allReservations.ifPresentOrElse(adminView::printAllReservations,
                adminView::printEmptyReservationMessage
        );
    }

    public User getAdmin(User user, String choice) {
        return AdminManager.getInstance().getAdmin(user, choice);
    }

    public User getEmptyAdmin() {
        return AdminManager.getInstance().buildAdmin();
    }

    public void showEmptySpaceMessage() {
        adminView.printEmptySpaceMessage();
    }

    public void showAllSpacesMessage() {
        adminView.printAllSpacesMessage();
    }

    public void showAddPrice() {
        adminView.printAddPrice();
    }

    public void showErrorAddMessage(String price, String type) {
        adminView.printErrorAddMessage(price, type);
    }
}

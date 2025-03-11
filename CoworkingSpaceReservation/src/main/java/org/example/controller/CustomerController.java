package org.example.controller;

import org.example.model.CustomerManager;
import org.example.model.Reservation;
import org.example.model.User;
import org.example.model.Workspace;
import org.example.view.CustomerView;

import java.util.List;
import java.util.Optional;

class CustomerController {

    private final CustomerView customerView;

    CustomerController(CustomerView customerView) {
        this.customerView = customerView;
    }

    static CustomerController createCustomerController() {
        CustomerView customerView = new CustomerView();
        return new CustomerController(customerView);
    }

    void showWelcomeMessage() {
        customerView.printWelcomeMessage();
    }

    void showMenu() {
        customerView.printMenu();
    }

    void showAvailableSpaces(List<Workspace> availableSpaces) {
        customerView.printAvailableSpaces(availableSpaces);
    }

    void showReservationStartDateMessage() {
        customerView.printReservationStartDateMessage();
    }

    void showReservationEndDateMessage() {
        customerView.printReservationEndDateMessage();
    }

    void showGetIdMessage() {
        customerView.printGetIdMessage();
    }

    void showUserReservations(Optional<List<Reservation>> userReservations) {
        userReservations.ifPresentOrElse(
                customerView::printCustomerReservations,
                customerView::printEmptyReservationMessage);
    }

    void showErrorRemoveReservationMessage(long id) {
        customerView.printErrorRemoveReservationMessage(id);
    }

    void showErrorReservationExistMessage() {
        customerView.printErrorReservationExistMessage();
    }

    User getCustomer(User user, String choice) {
        return CustomerManager.getInstance().getCustomer(user, choice);
    }

    void showBrowseSpacesItem() {
        customerView.printBrowseSpacesItem();
    }

    void printMakeReservationItem() {
        customerView.printMakeReservationItem();
    }

    void showViewReservationItem() {
        customerView.printViewReservationItem();
    }

    void showCancelReservationItem() {
        customerView.printCancelReservationItem();
    }
}

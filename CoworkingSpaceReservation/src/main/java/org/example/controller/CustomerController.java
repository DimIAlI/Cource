package org.example.controller;

import org.example.model.CustomerManager;
import org.example.model.Reservation;
import org.example.model.User;
import org.example.model.Workspace;
import org.example.view.CustomerView;

import java.util.List;
import java.util.Optional;

public class CustomerController {

    private final CustomerView customerView;

    public CustomerController(CustomerView customerView) {
        this.customerView = customerView;
    }

    static CustomerController createCustomerController() {
        CustomerView customerView = new CustomerView();
        return new CustomerController(customerView);
    }

    public void showWelcomeMessage() {
        customerView.printWelcomeMessage();

    }

    public void showMenu() {
        customerView.printMenu();
    }

    public void showReservationStartDateMessage() {
        customerView.printReservationStartDateMessage();
    }

    public void showReservationEndDateMessage() {
        customerView.printReservationEndDateMessage();
    }

    public void showAvailableSpaces(List<Workspace> availableSpaces) {
        customerView.printAvailableSpaces(availableSpaces);
    }

    public void showGetIdMessage() {
        customerView.printGetIdMessage();
    }

    public void showUserReservations(Optional<List<Reservation>> userReservations) {
        userReservations.ifPresentOrElse(
                customerView::printCustomerReservations,
                customerView::printEmptyReservationMessage);
    }

    public void showErrorRemoveReservationMessage(long id) {
        customerView.printErrorRemoveReservationMessage(id);
    }

    public User getCustomer(User user, String choice) {
        return CustomerManager.getInstance().getCustomer(user, choice);
    }

    public User getEmptyCustomer() {
        return CustomerManager.getInstance().buildCustomer();
    }

    public void showErrorReservationExistMessage() {
        customerView.printErrorReservationExistMessage();
    }
}

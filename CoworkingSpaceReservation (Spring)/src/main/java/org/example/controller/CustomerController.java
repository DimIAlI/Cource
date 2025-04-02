package org.example.controller;

import org.example.model.dto.space.ReservationDto;
import org.example.model.dto.account.UserDto;
import org.example.model.dto.space.WorkspaceDto;
import org.example.model.service.CustomerManager;
import org.example.view.CustomerView;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
class CustomerController {

    private final CustomerView customerView;
    private final CustomerManager customerManager;

    CustomerController(CustomerView customerView, CustomerManager customerManager) {
        this.customerView = customerView;
        this.customerManager = customerManager;
    }

    void showWelcomeMessage() {
        customerView.printWelcomeMessage();
    }

    void showMenu() {
        customerView.printMenu();
    }

    void showAvailableSpaces(List<WorkspaceDto> availableSpaces) {
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

    void showUserReservations(List<ReservationDto> userReservations) {
        if (userReservations.isEmpty()) {
            customerView.printEmptyReservationMessage();

        } else customerView.printCustomerReservations(userReservations);
    }

    void showErrorRemoveReservationMessage(long id) {
        customerView.printErrorRemoveReservationMessage(id);
    }

    void showErrorReservationExistMessage() {
        customerView.printErrorReservationExistMessage();
    }

    UserDto getCustomer(UserDto user, String choice) {
        return customerManager.getCustomer(user, choice);
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

    void showErrorAddReservationMessage() {
        customerView.printErrorAddReservationMessage();
    }

    public void showErrorNoAvailableSpacesMessage() {
        customerView.printErrorNoAvailableSpacesMessage();
    }

    public void showIdReservationMessage() {
        customerView.printIdReservationMessage();
    }
}

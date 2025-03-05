package org.example.controller;

import org.example.exceptions.*;
import org.example.model.Admin;
import org.example.model.SpaceType;
import org.example.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class MenuNavigator {

    private final GeneralController generalController;

    private MenuNavigator(GeneralController generalController) {
        this.generalController = generalController;
    }

    public static MenuNavigator menuNavigator(GeneralController generalController) {
        return new MenuNavigator(generalController);
    }

    public boolean mainMenuNavigator(User currentUser) {
        boolean isValid;
        String message;
        generalController.showMenu(currentUser);

        do {
            message = generalController.getUserMessage();
            isValid = ValueValidator.checkValue(currentUser, message);

            if (!isValid) {
                generalController.showErrorMessage();
                generalController.showEnterChoiceMessage();
            }
        } while (!isValid);

        if (!choiceOption(message, currentUser)) return true;

        generalController.showPressAnySymbolMessage();
        generalController.getUserMessage();
        return false;
    }

    //todo use the Strategy pattern
    private boolean adminMenuNavigator(String choice) {
        switch (choice) {
            case "1" -> {
                generalController.showAllSpacesMessage();

                String message;
                boolean isValid;

                do {
                    generalController.showAddType();
                    generalController.showEnterChoiceMessage();
                    message = generalController.getUserMessage();
                    isValid = ValueValidator.checkType(message);

                    if (!isValid) {
                        generalController.showErrorMessage();
                    }
                } while (!isValid);

                //used to avoid the effectively final restriction
                String finalMessage = message;
                SpaceType type = Arrays.stream(SpaceType.values())
                        .filter(t -> t.getDisplayName().equalsIgnoreCase(finalMessage))
                        .findFirst().get();

                do {
                    generalController.showAddPrice();
                    generalController.showEnterChoiceMessage();
                    message = generalController.getUserMessage();
                    isValid = ValueValidator.checkPrice(message);

                    if (!isValid) {
                        generalController.showErrorMessage();
                    }
                } while (!isValid);

                double price = Double.parseDouble(message);

                //Maybe it is possible for spaces with the same type and price to exist (they are not considered non-unique)
                try {
                    generalController.addWorkspace(type, price);
                    generalController.showSuccessMessage();
                } catch (PlaceAlreadyExistException e) {
                    generalController.showErrorAddMessage(e.getPrice(), e.getType());
                }
            }
            case "2" -> {

                if (!generalController.showAllSpacesMessage()) {
                    break;
                }
                generalController.showIdMessage();
                generalController.showEnterChoiceMessage();

                String message;
                boolean isValid;

                do {
                    message = generalController.getUserMessage();
                    isValid = ValueValidator.checkIdValue(message);

                    if (!isValid) {
                        generalController.showErrorMessage();
                    }
                } while (!isValid);

                long id = Long.parseLong(message);

                try {
                    generalController.removeWorkspace(id);

                } catch (IdNotFoundException e) {
                    generalController.showErrorIdMessage(e.getId());
                }
            }

            case "3" -> generalController.showAllReservations();

            case "4" -> {
                return true;
            }
        }
        return false;
    }

    //todo use the Strategy pattern

    private boolean customerMenuNavigator(String choice, User currentUser) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String message;
        boolean isValid;

        switch (choice) {

            case "1" -> {

                generalController.showReservationStartDateMessage();
                generalController.showEnterChoiceMessage();

                do {
                    message = generalController.getUserMessage();
                    isValid = ValueValidator.checkDataType(message, formatter);

                    if (!isValid) {
                        generalController.showErrorMessage();
                        generalController.showEnterChoiceMessage();
                    }
                } while (!isValid);

                LocalDateTime startTime = LocalDateTime.parse(message, formatter);

                generalController.showReservationEndDateMessage();
                generalController.showEnterChoiceMessage();

                do {
                    message = generalController.getUserMessage();
                    isValid = ValueValidator.checkDataTypeAndRange(startTime, message, formatter);

                    if (!isValid) {
                        generalController.showErrorMessage();
                        generalController.showEnterChoiceMessage();
                    }
                } while (!isValid);

                LocalDateTime endTime = LocalDateTime.parse(message, formatter);

                generalController.showAvailableSpaces(startTime, endTime);
            }

            case "2" -> {

                generalController.showGetIdMessage();
                generalController.showEnterChoiceMessage();

                do {
                    message = generalController.getUserMessage();
                    isValid = ValueValidator.checkIdValue(message);

                    if (!isValid) {
                        generalController.showErrorMessage();
                        generalController.showEnterChoiceMessage();
                    }
                } while (!isValid);

                long id = Long.parseLong(message);

                generalController.showReservationStartDateMessage();
                generalController.showEnterChoiceMessage();

                do {
                    message = generalController.getUserMessage();
                    isValid = ValueValidator.checkDataType(message, formatter);

                    if (!isValid) {
                        generalController.showErrorMessage();
                        generalController.showEnterChoiceMessage();
                    }
                } while (!isValid);

                LocalDateTime startTime = LocalDateTime.parse(message, formatter);

                generalController.showReservationEndDateMessage();
                generalController.showEnterChoiceMessage();

                do {
                    message = generalController.getUserMessage();
                    isValid = ValueValidator.checkDataTypeAndRange(startTime, message, formatter);

                    if (!isValid) {
                        generalController.showErrorMessage();
                        generalController.showEnterChoiceMessage();
                    }
                } while (!isValid);

                LocalDateTime endTime = LocalDateTime.parse(message, formatter);

                try {
                    generalController.addReservation(currentUser, id, startTime, endTime);
                    generalController.showSuccessMessage();
                } catch (IdNotFoundException e) {
                    generalController.showErrorIdMessage(e.getId());
                } catch (ReservationAlreadyExistException e) {
                    generalController.showErrorReservationExistMessage();
                }
            }

            case "3" -> generalController.showViewCustomerReservations(currentUser);

            case "4" -> {
                generalController.showIdMessage();
                generalController.showEnterChoiceMessage();

                do {
                    message = generalController.getUserMessage();
                    isValid = ValueValidator.checkIdValue(message);

                    if (!isValid) {
                        generalController.showErrorMessage();
                        generalController.showEnterChoiceMessage();
                    }
                } while (!isValid);

                int id = Integer.parseInt(message);

                try {
                    generalController.cancelReservation(id, currentUser);
                } catch (ReservationDoesNotExistException | UnauthorizedReservationAccessException e) {

                    generalController.showErrorRemoveReservationMessage(e.getId());
                }
            }
            case "5" -> {
                return true;
            }
        }
        return false;
    }

    private boolean choiceOption(String choice, User currentUser) {

        if (currentUser instanceof Admin) {
            return !adminMenuNavigator(choice);
        } else {
            return !customerMenuNavigator(choice, currentUser);
        }
    }
}

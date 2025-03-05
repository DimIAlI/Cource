package org.example;

import org.example.controller.GeneralController;
import org.example.controller.ValueValidator;
import org.example.exceptions.*;
import org.example.model.Admin;
import org.example.model.SpaceType;
import org.example.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Main {
    private final GeneralController generalController;
    private final ValueValidator valueValidator;

    public Main(GeneralController generalController, ValueValidator valueValidator) {
        this.generalController = generalController;
        this.valueValidator = valueValidator;
    }

    public static void main(String[] args) {
        Main main = createMain();

        while (true) {
            main.start();
        }
    }

    private static Main createMain() {
        GeneralController generalController = GeneralController.createGeneralController();
        ValueValidator valueValidator = new ValueValidator();
        return new Main(generalController, valueValidator);
    }

    private void start() {

        generalController.showWelcomeMessage();

        String message = getUserChoice();

        User emptyUser = getEmptyUser(message);

        String login = getUserLogin(emptyUser);

        User currentUser = findOrCreateUser(emptyUser, login);

        while (true) {
            if (mainMenuNavigator(currentUser)) break;
        }
    }

    private String getUserChoice() {
        String message;
        boolean isValid;

        do {
            generalController.showEnterChoiceMessage();
            message = generalController.getUserMessage();
            isValid = valueValidator.checkValue(message);

            if (!isValid) {
                generalController.showErrorMessage();
            }
        } while (!isValid);
        return message;
    }

    private User getEmptyUser(String choice) {

        switch (choice) {
            case "1" -> {
                return generalController.getEmptyAdmin();
            }
            case "2" -> {
                return generalController.getEmptyCustomer();
            }
            case "3" -> {
                exitProgram();
                throw new ProgramShouldBeClosedException();
            }
            default -> throw new IllegalArgumentException("Invalid choice: " + choice);
        }
    }

    private String getUserLogin(User emptyUser) {
        String login;
        boolean isValid;

        do {
            generalController.showWelcomeMessage(emptyUser);
            login = generalController.getUserMessage().toLowerCase();
            isValid = valueValidator.checkLogin(login);
            if (!isValid) {
                generalController.showErrorLoginMessage();
            }
        } while (!isValid);
        return login;
    }

    private User findOrCreateUser(User user, String choice) {

        if (user instanceof Admin) {
            return generalController.getAdmin(user, choice);
        }
        return generalController.getCustomer(user, choice);
    }
    private boolean mainMenuNavigator(User currentUser) {
        boolean isValid;
        String message;
        generalController.showMenu(currentUser);

        do {
            message = generalController.getUserMessage();
            isValid = valueValidator.checkValue(currentUser, message);

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

    //There's no need to pass the User because it doesn't matter which admin logged in, but still...
    private boolean choiceOption(String choice, User currentUser) {

        if (currentUser instanceof Admin) {
            return !adminMenuNavigator(choice);
        } else {
            return !customerMenuNavigator(choice, currentUser);
        }
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
                    isValid = valueValidator.checkType(message);

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
                    isValid = valueValidator.checkPrice(message);

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
                    isValid = valueValidator.checkIdValue(message);

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
                    isValid = valueValidator.checkDataType(message, formatter);

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
                    isValid = valueValidator.checkDataTypeAndRange(startTime, message, formatter);

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
                    isValid = valueValidator.checkIdValue(message);

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
                    isValid = valueValidator.checkDataType(message, formatter);

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
                    isValid = valueValidator.checkDataTypeAndRange(startTime, message, formatter);

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
                }catch (ReservationAlreadyExistException e){
                    generalController.showErrorReservationExistMessage();
                }
            }

            case "3" -> generalController.showViewCustomerReservations(currentUser);

            case "4" -> {
                generalController.showIdMessage();
                generalController.showEnterChoiceMessage();

                do {
                    message = generalController.getUserMessage();
                    isValid = valueValidator.checkIdValue(message);

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

    private void exitProgram() {
        generalController.showExitMessage();
        generalController.saveChanges();
        System.exit(0);
    }
}

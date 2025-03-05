package org.example.controller;

import org.example.model.Admin;
import org.example.model.User;

public class UserSessionHandler {

    private final GeneralController generalController;

    private UserSessionHandler(GeneralController generalController) {
        this.generalController = generalController;
    }

    public static UserSessionHandler userSessionHandler(GeneralController generalController) {
        return new UserSessionHandler(generalController);
    }

    public String getUserChoice() {
        String message;
        boolean isValid;

        do {
            generalController.showEnterChoiceMessage();
            message = generalController.getUserMessage();
            isValid = ValueValidator.checkValue(message);

            if (!isValid) {
                generalController.showErrorMessage();
            }
        } while (!isValid);
        return message;
    }

    public User getEmptyUser(String choice) {

        switch (choice) {
            case "1" -> {
                return generalController.getEmptyAdmin();
            }
            case "2" -> {
                return generalController.getEmptyCustomer();
            }
            default -> throw new IllegalArgumentException("Invalid choice: " + choice);
        }
    }

    public String getUserLogin(User emptyUser) {
        String login;
        boolean isValid;

        do {
            generalController.showWelcomeMessage(emptyUser);
            login = generalController.getUserMessage().toLowerCase();
            isValid = ValueValidator.checkLogin(login);
            if (!isValid) {
                generalController.showErrorLoginMessage();
            }
        } while (!isValid);
        return login;
    }

    public User findOrCreateUser(User user, String choice) {

        if (user instanceof Admin) {
            return generalController.getAdmin(user, choice);
        }
        return generalController.getCustomer(user, choice);
    }
}

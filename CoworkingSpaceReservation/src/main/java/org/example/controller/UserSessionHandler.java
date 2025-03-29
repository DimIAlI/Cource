package org.example.controller;

import org.example.model.dto.account.UserDto;
import org.example.controller.factory.UserFactorySelector;

import java.util.Optional;

public class UserSessionHandler {

    private final GeneralController generalController;
    private final UserFactorySelector userFactorySelector;

    private UserSessionHandler(GeneralController generalController, UserFactorySelector userFactorySelector) {
        this.generalController = generalController;
        this.userFactorySelector = userFactorySelector;
    }

    public static UserSessionHandler userSessionHandler(GeneralController generalController) {
        UserFactorySelector userFactorySelector = new UserFactorySelector();
        return new UserSessionHandler(generalController, userFactorySelector);
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

    public Optional<UserDto> getEmptyUser(String choice) {
        return userFactorySelector.getEmptyUser(choice);
    }

    public String getUserLogin(UserDto emptyUser) {
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
}

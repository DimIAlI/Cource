package org.example.controller;

import org.example.model.dto.account.UserDto;
import org.example.controller.factory.UserFactorySelector;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class UserSessionHandler {

    private final GeneralController generalController;
    private final UserFactorySelector userFactorySelector;
    private final ValueValidator valueValidator;

    private UserSessionHandler(GeneralController generalController, UserFactorySelector userFactorySelector, ValueValidator valueValidator) {
        this.generalController = generalController;
        this.userFactorySelector = userFactorySelector;
        this.valueValidator = valueValidator;
    }

    public Optional<UserDto> getEmptyUserBasedOnChoice() {
        String userChoice = getUserChoice();
        return getEmptyUser(userChoice);
    }

    public String getUserLogin(UserDto emptyUser) {
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

    private Optional<UserDto> getEmptyUser(String choice) {
        return userFactorySelector.getEmptyUser(choice);
    }
}

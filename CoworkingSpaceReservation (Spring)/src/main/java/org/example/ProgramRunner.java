package org.example;

import org.example.controller.GeneralController;
import org.example.controller.navigation.MenuNavigator;
import org.example.controller.UserSessionHandler;
import org.example.model.dto.account.UserDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProgramRunner {
    private final GeneralController generalController;
    private final UserSessionHandler sessionHandler;
    private final MenuNavigator navigator;

    ProgramRunner(GeneralController generalController, UserSessionHandler sessionHandler, MenuNavigator navigator) {
        this.generalController = generalController;
        this.sessionHandler = sessionHandler;
        this.navigator = navigator;
    }

    void run() {

        generalController.showWelcomeMessage();

        while (true) {
            generalController.showMenu();

            Optional<UserDto> optionalUser =  sessionHandler.getEmptyUserBasedOnChoice();

            UserDto user = optionalUser.orElseGet(() -> {
                exitProgram();
                return null;
            });

            String login = sessionHandler.getUserLogin(user);

            UserDto currentUser = generalController.getUser(user, login);

            navigator.navigateMainMenu(currentUser);
        }
    }

    private void exitProgram() {
        generalController.showExitMessage();
        System.exit(0);
    }
}

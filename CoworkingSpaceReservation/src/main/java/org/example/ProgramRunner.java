package org.example;

import org.example.controller.GeneralController;
import org.example.controller.navigation.MenuNavigator;
import org.example.controller.UserSessionHandler;
import org.example.model.dto.account.UserDto;

import java.util.Optional;

public class ProgramRunner {

    private final GeneralController generalController;
    private final UserSessionHandler sessionHandler;
    private final MenuNavigator navigator;

    private ProgramRunner(GeneralController generalController, UserSessionHandler sessionHandler, MenuNavigator navigator) {
        this.generalController = generalController;
        this.sessionHandler = sessionHandler;
        this.navigator = navigator;
    }

    public static ProgramRunner createRunner() {
        GeneralController generalController = GeneralController.createGeneralController();
        UserSessionHandler sessionHandler = UserSessionHandler.userSessionHandler(generalController);
        MenuNavigator navigator = MenuNavigator.menuNavigator(generalController);
        return new ProgramRunner(generalController, sessionHandler, navigator);
    }

    public void run() {

        generalController.showWelcomeMessage();

        while (true) {
            generalController.showMenu();

            String message = sessionHandler.getUserChoice();

            Optional<UserDto> optionalUser = sessionHandler.getEmptyUser(message);

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

package org.example;

import org.example.controller.GeneralController;
import org.example.controller.MenuNavigator;
import org.example.controller.UserSessionHandler;
import org.example.model.User;

public class ProgramRunner {

    private final GeneralController generalController;
    private final UserSessionHandler sessionHandler;
    private final MenuNavigator navigator;

    public static ProgramRunner createRunner() {
        GeneralController generalController = GeneralController.createGeneralController();
        UserSessionHandler sessionHandler = UserSessionHandler.userSessionHandler(generalController);
        MenuNavigator navigator = MenuNavigator.menuNavigator(generalController);
        return new ProgramRunner(generalController, sessionHandler, navigator);
    }

    private ProgramRunner(GeneralController generalController, UserSessionHandler sessionHandler, MenuNavigator navigator) {
        this.generalController = generalController;
        this.sessionHandler = sessionHandler;
        this.navigator = navigator;
    }

    public void run() {

        generalController.showWelcomeMessage();

        while (true) {
            generalController.showMenu();

            String message = sessionHandler.getUserChoice();

            if (message.equals("3")) exitProgram();

            User emptyUser = sessionHandler.getEmptyUser(message);

            String login = sessionHandler.getUserLogin(emptyUser);

            User currentUser = sessionHandler.findOrCreateUser(emptyUser, login);

            while (true) {
                if (navigator.mainMenuNavigator(currentUser)) break;
            }
        }
    }

    private void exitProgram() {
        generalController.showExitMessage();
        generalController.saveChanges();
        System.exit(0);
    }
}

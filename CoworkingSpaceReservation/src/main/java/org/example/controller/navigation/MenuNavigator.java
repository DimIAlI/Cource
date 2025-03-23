package org.example.controller.navigation;

import org.example.controller.GeneralController;
import org.example.controller.ValueValidator;
import org.example.model.entity.AdminEntity;
import org.example.model.entity.CustomerEntity;
import org.example.model.entity.UserEntity;


public class MenuNavigator {

    private final GeneralController generalController;

    private MenuNavigator(GeneralController generalController) {
        this.generalController = generalController;
    }

    public static MenuNavigator menuNavigator(GeneralController generalController) {
        return new MenuNavigator(generalController);
    }

    public void navigateMainMenu(UserEntity currentUser) {
        while (true) {
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

            if (navigateWithStrategy(currentUser, message)) break;
        }
    }

    private boolean navigateWithStrategy(UserEntity currentUser, String message) {

        MenuStrategy strategy = (currentUser instanceof AdminEntity) ? new AdminMenuStrategy() : new CustomerMenuStrategy((CustomerEntity) currentUser);
        boolean exit = strategy.navigate(message, generalController);

        generalController.showPressAnySymbolMessage();
        generalController.getUserMessage();
        return exit;
    }
}

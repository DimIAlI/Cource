package org.example.controller.navigation;

import org.example.controller.GeneralController;
import org.example.controller.ValueValidator;
import org.example.model.dto.account.AdminDto;
import org.example.model.dto.account.CustomerDto;
import org.example.model.dto.account.UserDto;

public class MenuNavigator {

    private final GeneralController generalController;

    private MenuNavigator(GeneralController generalController) {
        this.generalController = generalController;
    }

    public static MenuNavigator menuNavigator(GeneralController generalController) {
        return new MenuNavigator(generalController);
    }

    public void navigateMainMenu(UserDto currentUser) {
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

    private boolean navigateWithStrategy(UserDto currentUser, String message) {

        MenuStrategy strategy = (currentUser instanceof AdminDto) ? new AdminMenuStrategy() : new CustomerMenuStrategy((CustomerDto) currentUser);
        boolean exit = strategy.navigate(message, generalController);

        generalController.showPressAnySymbolMessage();
        generalController.getUserMessage();
        return exit;
    }
}

package org.example.controller.navigation;

import org.example.config.ApplicationConfiguration;
import org.example.controller.GeneralController;
import org.example.controller.ValueValidator;
import org.example.model.dto.account.AdminDto;
import org.example.model.dto.account.UserDto;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MenuNavigator {

    private final GeneralController generalController;
    private final ValueValidator valueValidator;

    private MenuNavigator(GeneralController generalController, ValueValidator valueValidator) {
        this.generalController = generalController;
        this.valueValidator = valueValidator;
    }

    public void navigateMainMenu(UserDto currentUser) {
        while (true) {
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

            if (navigateWithStrategy(currentUser, message)) break;
        }
    }

    private boolean navigateWithStrategy(UserDto currentUser, String message) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

        MenuStrategy strategy = (currentUser instanceof AdminDto) ?
             context.getBean(AdminMenuStrategy.class): context.getBean(CustomerMenuStrategy.class);

        boolean exit = strategy.navigate(message, generalController, currentUser);

        generalController.showPressAnySymbolMessage();
        generalController.getUserMessage();
        return exit;
    }
}

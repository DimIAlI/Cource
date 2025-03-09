package org.example.controller.navigation.command.admin;

import org.example.controller.GeneralController;
import org.example.controller.ValueValidator;
import org.example.controller.navigation.command.MenuCommand;
import org.example.exceptions.PlaceAlreadyExistException;
import org.example.model.SpaceType;

import java.util.Arrays;

public class AddWorkspaceCommand implements MenuCommand {
    @Override
    public boolean execute(GeneralController generalController) {
        generalController.showAllSpacesMessage();

        String message;
        boolean isValid;

        do {
            generalController.showAddType();
            generalController.showEnterChoiceMessage();
            message = generalController.getUserMessage();
            isValid = ValueValidator.checkType(message);

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
            isValid = ValueValidator.checkPrice(message);

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
        return false;
    }
}

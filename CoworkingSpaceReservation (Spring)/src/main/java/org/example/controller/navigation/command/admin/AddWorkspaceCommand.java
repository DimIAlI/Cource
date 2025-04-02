package org.example.controller.navigation.command.admin;

import org.example.controller.GeneralController;
import org.example.controller.ValueValidator;
import org.example.controller.navigation.command.MenuCommand;
import org.example.exceptions.PlaceAlreadyExistException;
import org.example.model.dto.account.UserDto;
import org.example.model.dto.space.SpaceTypeDto;
import org.example.model.service.SpaceTypeManager;
import org.springframework.stereotype.Component;

@Component
public class AddWorkspaceCommand implements MenuCommand {
    private final ValueValidator valueValidator;
    private final SpaceTypeManager spaceTypeManager;

    public AddWorkspaceCommand(ValueValidator valueValidator, SpaceTypeManager spaceTypeManager) {
        this.valueValidator = valueValidator;
        this.spaceTypeManager = spaceTypeManager;
    }

    @Override
    public boolean execute(GeneralController generalController, UserDto userDto){
        generalController.showAddSpaceMenuItem();
        generalController.showAllSpacesMessage();

        String message;
        boolean isValid;

        do {
            generalController.showAddType();
            generalController.showEnterChoiceMessage();
            message = generalController.getUserMessage();
            isValid = valueValidator.checkType(message);

            if (!isValid) {
                generalController.showErrorMessage();
            }
        } while (!isValid);

        //used to avoid the effectively final restriction
        String finalMessage = message;
        SpaceTypeDto type = spaceTypeManager.getValues().values().stream()
                .filter(t -> t.getDisplayName().equalsIgnoreCase(finalMessage))
                .findFirst().get();

        do {
            generalController.showAddPrice();
            generalController.showEnterChoiceMessage();
            message = generalController.getUserMessage();
            isValid = valueValidator.checkPrice(message);

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

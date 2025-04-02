package org.example.controller.navigation.command.admin;

import org.example.controller.GeneralController;
import org.example.controller.ValueValidator;
import org.example.controller.navigation.command.MenuCommand;
import org.example.exceptions.IdNotFoundException;
import org.example.model.dto.account.UserDto;
import org.springframework.stereotype.Component;

@Component
public class RemoveWorkspaceCommand implements MenuCommand {
    private final ValueValidator valueValidator;

    public RemoveWorkspaceCommand(ValueValidator valueValidator) {
        this.valueValidator = valueValidator;
    }

    @Override
    public boolean execute(GeneralController generalController, UserDto userDto) {
        generalController.showRemoveSpaceMenuItem();
        if (!generalController.showAllSpacesMessage()) {
            return false;
        }
        generalController.showIdMessage();
        generalController.showEnterChoiceMessage();

        String message;
        boolean isValid;

        do {
            message = generalController.getUserMessage();
            isValid = valueValidator.checkIdValue(message);

            if (!isValid) {
                generalController.showErrorMessage();
            }
        } while (!isValid);

        long id = Long.parseLong(message);

        try {
            generalController.removeWorkspace(id);
            generalController.showSuccessMessage();
        } catch (IdNotFoundException e) {
            generalController.showErrorIdMessage(e.getId());
        }
        return false;
    }
}

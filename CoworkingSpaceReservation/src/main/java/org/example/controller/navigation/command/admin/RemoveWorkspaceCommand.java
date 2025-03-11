package org.example.controller.navigation.command.admin;

import org.example.controller.GeneralController;
import org.example.controller.ValueValidator;
import org.example.controller.navigation.command.MenuCommand;
import org.example.exceptions.IdNotFoundException;

public class RemoveWorkspaceCommand implements MenuCommand {
    @Override
    public boolean execute(GeneralController generalController) {
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
            isValid = ValueValidator.checkIdValue(message);

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

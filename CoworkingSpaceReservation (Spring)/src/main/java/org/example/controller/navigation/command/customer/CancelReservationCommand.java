package org.example.controller.navigation.command.customer;

import org.example.controller.GeneralController;
import org.example.controller.ValueValidator;
import org.example.controller.navigation.command.MenuCommand;
import org.example.exceptions.ReservationNotFoundForUserException;
import org.example.model.dto.account.UserDto;
import org.springframework.stereotype.Component;

@Component
public class CancelReservationCommand implements MenuCommand {
    private final ValueValidator valueValidator;
    public CancelReservationCommand(ValueValidator valueValidator) {
        this.valueValidator = valueValidator;
    }

    @Override
    public boolean execute(GeneralController generalController, UserDto userDto) {
        String message;
        boolean isValid;
        generalController.showCancelReservationItem();
        generalController.showIdReservationMessage();
        generalController.showEnterChoiceMessage();

        do {
            message = generalController.getUserMessage();
            isValid = valueValidator.checkIdValue(message);

            if (!isValid) {
                generalController.showErrorMessage();
                generalController.showEnterChoiceMessage();
            }
        } while (!isValid);

        int id = Integer.parseInt(message);

        try {
            generalController.cancelReservation(id, userDto);
            generalController.showSuccessMessage();

        } catch (ReservationNotFoundForUserException e) {

            generalController.showErrorRemoveReservationMessage(e.getId());
        }
        return false;
    }
}

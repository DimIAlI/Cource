package org.example.controller.navigation.command.customer;

import org.example.controller.GeneralController;
import org.example.controller.ValueValidator;
import org.example.exceptions.ReservationNotFoundForUserException;
import org.example.model.dto.CustomerDto;

public class CancelReservationCommand extends CustomerCommand {
    public CancelReservationCommand(CustomerDto customer) {
        super(customer);
    }

    @Override
    public boolean execute(GeneralController generalController) {
        String message;
        boolean isValid;
        generalController.showCancelReservationItem();
        generalController.showIdMessage();
        generalController.showEnterChoiceMessage();

        do {
            message = generalController.getUserMessage();
            isValid = ValueValidator.checkIdValue(message);

            if (!isValid) {
                generalController.showErrorMessage();
                generalController.showEnterChoiceMessage();
            }
        } while (!isValid);

        int id = Integer.parseInt(message);

        try {
            generalController.cancelReservation(id, getCustomer());
            generalController.showSuccessMessage();

        } catch (ReservationNotFoundForUserException e) {

            generalController.showErrorRemoveReservationMessage(e.getId());
        }
        return false;
    }
}

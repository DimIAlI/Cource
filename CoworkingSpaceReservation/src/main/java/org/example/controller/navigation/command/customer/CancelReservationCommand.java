package org.example.controller.navigation.command.customer;

import org.example.controller.GeneralController;
import org.example.controller.ValueValidator;
import org.example.exceptions.ReservationDoesNotExistException;
import org.example.exceptions.UnauthorizedReservationAccessException;
import org.example.model.Customer;

public class CancelReservationCommand extends CustomerCommand {
    public CancelReservationCommand(Customer customer) {
        super(customer);
    }

    @Override
    public boolean execute(GeneralController generalController) {
        String message;
        boolean isValid;

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
        } catch (ReservationDoesNotExistException | UnauthorizedReservationAccessException e) {

            generalController.showErrorRemoveReservationMessage(e.getId());
        }
        return false;
    }
}

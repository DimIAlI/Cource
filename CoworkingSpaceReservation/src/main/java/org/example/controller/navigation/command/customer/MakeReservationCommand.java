package org.example.controller.navigation.command.customer;

import org.example.controller.GeneralController;
import org.example.controller.ValueValidator;
import org.example.exceptions.IdNotFoundException;
import org.example.exceptions.ReservationAlreadyExistException;
import org.example.exceptions.WorkspaceAlreadyBookedAtTimeException;
import org.example.model.dto.CustomerDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MakeReservationCommand extends CustomerCommand {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public MakeReservationCommand(CustomerDto customer) {
        super(customer);
    }

    @Override
    public boolean execute(GeneralController generalController) {
        String message;
        boolean isValid;
        generalController.showMakeReservationItem();
        generalController.showGetIdMessage();
        generalController.showEnterChoiceMessage();

        do {
            message = generalController.getUserMessage();
            isValid = ValueValidator.checkIdValue(message);

            if (!isValid) {
                generalController.showErrorMessage();
                generalController.showEnterChoiceMessage();
            }
        } while (!isValid);

        long id = Long.parseLong(message);

        generalController.showReservationStartDateMessage();
        generalController.showEnterChoiceMessage();

        do {
            message = generalController.getUserMessage();
            isValid = ValueValidator.checkDataType(message, formatter);

            if (!isValid) {
                generalController.showErrorMessage();
                generalController.showEnterChoiceMessage();
            }
        } while (!isValid);

        LocalDateTime startTime = LocalDateTime.parse(message, formatter);

        generalController.showReservationEndDateMessage();
        generalController.showEnterChoiceMessage();

        do {
            message = generalController.getUserMessage();
            isValid = ValueValidator.checkDataTypeAndRange(startTime, message, formatter);

            if (!isValid) {
                generalController.showErrorMessage();
                generalController.showEnterChoiceMessage();
            }
        } while (!isValid);

        LocalDateTime endTime = LocalDateTime.parse(message, formatter);

        try {
            generalController.addReservation(getCustomer(), id, startTime, endTime);
            generalController.showSuccessMessage();

        } catch (IdNotFoundException e) {
            generalController.showErrorIdMessage(e.getId());
        } catch (ReservationAlreadyExistException e) {
            generalController.showErrorReservationExistMessage();
        } catch (WorkspaceAlreadyBookedAtTimeException e) {
            generalController.showErrorAddReservationMessage();
        }
        return false;
    }
}

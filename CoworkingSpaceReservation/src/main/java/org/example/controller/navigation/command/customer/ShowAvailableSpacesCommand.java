package org.example.controller.navigation.command.customer;

import org.example.controller.GeneralController;
import org.example.controller.ValueValidator;
import org.example.exceptions.NoAvailableSpacesException;
import org.example.model.dto.account.CustomerDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ShowAvailableSpacesCommand extends CustomerCommand {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public ShowAvailableSpacesCommand(CustomerDto customer) {
        super(customer);
    }

    @Override
    public boolean execute(GeneralController generalController) {
        String message;
        boolean isValid;

        generalController.showBrowseSpacesItem();
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
            generalController.showAvailableSpaces(startTime, endTime);
        }catch (NoAvailableSpacesException e){
            generalController.showErrorNoAvailableSpacesMessage();
        }
        return false;
    }
}

package org.example.controller.navigation.command.customer;

import org.example.controller.GeneralController;
import org.example.controller.ValueValidator;
import org.example.controller.navigation.command.MenuCommand;
import org.example.exceptions.NoAvailableSpacesException;
import org.example.model.dto.account.UserDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Component
public class ShowAvailableSpacesCommand implements MenuCommand {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private final ValueValidator valueValidator;

    public ShowAvailableSpacesCommand(ValueValidator valueValidator) {
        this.valueValidator = valueValidator;
    }

    @Override
    public boolean execute(GeneralController generalController, UserDto userDto) {
        String message;
        boolean isValid;

        generalController.showBrowseSpacesItem();
        generalController.showReservationStartDateMessage();
        generalController.showEnterChoiceMessage();

        do {
            message = generalController.getUserMessage();
            isValid = valueValidator.checkDataType(message, formatter);

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
            isValid = valueValidator.checkDataTypeAndRange(startTime, message, formatter);

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

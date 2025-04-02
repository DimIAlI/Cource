package org.example.controller.navigation.command.customer;

import org.example.controller.GeneralController;
import org.example.controller.navigation.command.MenuCommand;
import org.example.model.dto.account.UserDto;
import org.springframework.stereotype.Component;

@Component
public class ViewReservationsCommand implements MenuCommand {

    @Override
    public boolean execute(GeneralController generalController, UserDto userDto) {
        generalController.showViewReservationItem();
        generalController.showViewCustomerReservations(userDto);
        return false;
    }
}

package org.example.controller.navigation.command.admin;

import org.example.controller.GeneralController;
import org.example.controller.navigation.command.MenuCommand;
import org.example.model.dto.account.UserDto;
import org.springframework.stereotype.Component;

@Component
public class ShowAllReservationCommand implements MenuCommand {
    @Override
    public boolean execute(GeneralController generalController, UserDto userDto) {
        generalController.showViewReservationMenuItem();
        generalController.showAllReservations();
        return false;
    }
}
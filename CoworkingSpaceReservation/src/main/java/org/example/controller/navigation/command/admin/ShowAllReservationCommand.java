package org.example.controller.navigation.command.admin;

import org.example.controller.GeneralController;
import org.example.controller.navigation.command.MenuCommand;

public class ShowAllReservationCommand implements MenuCommand {
    @Override
    public boolean execute(GeneralController generalController) {
        generalController.showViewReservationMenuItem();
        generalController.showAllReservations();
        return false;
    }
}

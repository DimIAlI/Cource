package org.example.controller.navigation;

import org.example.controller.GeneralController;
import org.example.controller.navigation.command.LogoutCommand;
import org.example.controller.navigation.command.MenuCommand;
import org.example.controller.navigation.command.customer.CancelReservationCommand;
import org.example.controller.navigation.command.customer.MakeReservationCommand;
import org.example.controller.navigation.command.customer.ShowAvailableSpacesCommand;
import org.example.controller.navigation.command.customer.ViewReservationsCommand;
import org.example.model.dto.CustomerDto;

import java.util.Map;

public class CustomerMenuStrategy implements MenuStrategy {
    private final Map<String, MenuCommand> commands;

    public CustomerMenuStrategy(CustomerDto currentCustomer) {
        commands = Map.of("1", new ShowAvailableSpacesCommand(currentCustomer),
                "2", new MakeReservationCommand(currentCustomer),
                "3", new ViewReservationsCommand(currentCustomer),
                "4", new CancelReservationCommand(currentCustomer),
                "5", new LogoutCommand());
    }

    @Override
    public boolean navigate(String choice, GeneralController generalController) {
        return commands.get(choice).execute(generalController);
    }
}

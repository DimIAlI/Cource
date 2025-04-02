package org.example.controller.navigation;

import org.example.controller.GeneralController;
import org.example.controller.ValueValidator;
import org.example.controller.navigation.command.LogoutCommand;
import org.example.controller.navigation.command.MenuCommand;
import org.example.controller.navigation.command.customer.CancelReservationCommand;
import org.example.controller.navigation.command.customer.MakeReservationCommand;
import org.example.controller.navigation.command.customer.ShowAvailableSpacesCommand;
import org.example.controller.navigation.command.customer.ViewReservationsCommand;
import org.example.model.dto.account.UserDto;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class CustomerMenuStrategy implements MenuStrategy {
    private final Map<String, MenuCommand> commands;

    public CustomerMenuStrategy(ValueValidator valueValidator) {

        commands = Map.of("1", new ShowAvailableSpacesCommand(valueValidator),
                "2", new MakeReservationCommand(valueValidator),
                "3", new ViewReservationsCommand(),
                "4", new CancelReservationCommand(valueValidator),
                "5", new LogoutCommand());
    }

    @Override
    public boolean navigate(String choice, GeneralController generalController, UserDto userDto) {
        return commands.get(choice).execute(generalController, userDto);
    }
}

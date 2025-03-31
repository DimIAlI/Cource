package org.example.controller.navigation;

import org.example.controller.GeneralController;
import org.example.controller.navigation.command.*;
import org.example.controller.navigation.command.admin.AddWorkspaceCommand;
import org.example.controller.navigation.command.LogoutCommand;
import org.example.controller.navigation.command.admin.RemoveWorkspaceCommand;
import org.example.controller.navigation.command.admin.ShowAllReservationCommand;
import org.example.model.dto.account.UserDto;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class AdminMenuStrategy implements MenuStrategy {
    private final Map<String, MenuCommand> commands;

    public AdminMenuStrategy(AddWorkspaceCommand addWorkspaceCommand,
                             RemoveWorkspaceCommand removeWorkspaceCommand,
                             ShowAllReservationCommand showAllReservationCommand,
                             LogoutCommand logoutCommand) {

        commands = Map.of(
                "1", addWorkspaceCommand,
                "2", removeWorkspaceCommand,
                "3", showAllReservationCommand,
                "4", logoutCommand
        );
    }

    @Override
    public boolean navigate(String choice, GeneralController generalController, UserDto userDto) {
        return commands.get(choice).execute(generalController, userDto);
    }
}

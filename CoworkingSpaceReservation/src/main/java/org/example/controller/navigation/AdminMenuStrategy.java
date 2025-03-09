package org.example.controller.navigation;

import org.example.controller.GeneralController;
import org.example.controller.navigation.command.*;
import org.example.controller.navigation.command.admin.AddWorkspaceCommand;
import org.example.controller.navigation.command.LogoutCommand;
import org.example.controller.navigation.command.admin.RemoveWorkspaceCommand;
import org.example.controller.navigation.command.admin.ShowAllReservationCommand;

import java.util.Map;

public class AdminMenuStrategy implements MenuStrategy {
    private final Map<String, MenuCommand> commands;

    public AdminMenuStrategy() {
        commands = Map.of("1", new AddWorkspaceCommand(),
                "2", new RemoveWorkspaceCommand(),
                "3", new ShowAllReservationCommand(),
                "4", new LogoutCommand());
    }

    @Override
    public boolean navigate(String choice, GeneralController generalController) {
        return commands.get(choice).execute(generalController);
    }
}

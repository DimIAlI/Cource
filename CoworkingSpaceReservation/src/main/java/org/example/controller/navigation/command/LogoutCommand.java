package org.example.controller.navigation.command;

import org.example.controller.GeneralController;

public class LogoutCommand implements MenuCommand {
    @Override
    public boolean execute(GeneralController generalController) {
        return true;
    }
}

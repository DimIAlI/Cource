package org.example.controller.navigation.command;

import org.example.controller.GeneralController;
import org.example.model.dto.account.UserDto;
import org.springframework.stereotype.Component;

@Component
public class LogoutCommand implements MenuCommand {
    @Override
    public boolean execute(GeneralController generalController, UserDto userDto) {
        return true;
    }
}

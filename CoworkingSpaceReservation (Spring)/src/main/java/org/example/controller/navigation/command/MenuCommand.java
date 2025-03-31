package org.example.controller.navigation.command;

import org.example.controller.GeneralController;
import org.example.model.dto.account.UserDto;

public interface MenuCommand {
    boolean execute(GeneralController generalController, UserDto userDto);
}

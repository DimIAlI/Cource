package org.example.controller.navigation;


import org.example.controller.GeneralController;
import org.example.model.dto.account.UserDto;

public interface MenuStrategy {
    boolean navigate(String choice, GeneralController generalController, UserDto userDto);
}

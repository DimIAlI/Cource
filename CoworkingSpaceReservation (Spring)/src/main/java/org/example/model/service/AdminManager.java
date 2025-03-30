package org.example.model.service;

import org.example.model.dto.account.AdminDto;
import org.example.model.dto.account.UserDto;

public class AdminManager {

    private static final AdminManager INSTANCE = new AdminManager();

    private AdminManager() {
    }

    public static AdminManager getInstance() {
        return INSTANCE;
    }

    public UserDto getAdmin(UserDto user, String login) {
        return LoginManager.getInstance().authenticateOrRegister(user, login);
    }

    public AdminDto getEmptyAdmin() {
        return AdminDto.builder().build();
    }
}

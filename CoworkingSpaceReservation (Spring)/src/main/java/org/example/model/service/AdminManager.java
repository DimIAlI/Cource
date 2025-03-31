package org.example.model.service;

import org.example.model.dto.account.AdminDto;
import org.example.model.dto.account.UserDto;
import org.springframework.stereotype.Service;

@Service
public class AdminManager {
    private final LoginManager loginManager;

    public AdminManager(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    public UserDto getAdmin(UserDto user, String login) {
        return loginManager.authenticateOrRegister(user, login);
    }

    public AdminDto getEmptyAdmin() {
        return AdminDto.builder().build();
    }
}

package org.example.model.service;

import org.example.model.dto.account.CustomerDto;
import org.example.model.dto.account.UserDto;
import org.springframework.stereotype.Service;

@Service
public class CustomerManager {

    private final LoginManager loginManager;

    public CustomerManager(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    public UserDto getCustomer(UserDto user, String login) {
        return loginManager.authenticateOrRegister(user, login);
    }

    public CustomerDto getEmptyCustomer() {
        return CustomerDto.builder().build();
    }
}

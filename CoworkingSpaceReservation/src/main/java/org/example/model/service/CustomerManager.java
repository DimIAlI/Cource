package org.example.model.service;

import org.example.model.dto.CustomerDto;
import org.example.model.dto.UserDto;

public class CustomerManager {

    private static final CustomerManager INSTANCE = new CustomerManager();

    private CustomerManager() {
    }

    public static CustomerManager getInstance() {
        return INSTANCE;
    }

    public UserDto getCustomer(UserDto user, String login) {
        return LoginManager.getInstance().authenticateOrRegister(user, login);
    }

    public CustomerDto buildCustomer() {
        return CustomerDto.builder().build();
    }
}

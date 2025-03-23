package org.example.model.service;

import org.example.model.entity.CustomerEntity;
import org.example.model.entity.UserEntity;

public class CustomerManager {

    private static final CustomerManager INSTANCE = new CustomerManager();

    private CustomerManager() {
    }

    public static CustomerManager getInstance() {
        return INSTANCE;
    }

    public UserEntity getCustomer(UserEntity user, String login) {
        return LoginManager.getInstance().authenticateOrRegister(user, login);
    }

    public CustomerEntity buildCustomer() {
        return CustomerEntity.builder().build();
    }

    UserEntity setCustomerLogin(UserEntity user, String choice) {
        user.setLogin(choice);
        return user;
    }
}

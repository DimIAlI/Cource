package org.example.model.service;

import org.example.model.entity.Customer;
import org.example.model.entity.User;

public class CustomerManager {

    private static final CustomerManager INSTANCE = new CustomerManager();

    private CustomerManager() {
    }

    public static CustomerManager getInstance() {
        return INSTANCE;
    }

    public User getCustomer(User user, String login) {
        return LoginManager.getInstance().authenticateOrRegister(user, login);
    }

    public Customer buildCustomer() {
        return Customer.builder().build();
    }

    User setCustomerLogin(User user, String choice) {
        user.setLogin(choice);
        return user;
    }
}

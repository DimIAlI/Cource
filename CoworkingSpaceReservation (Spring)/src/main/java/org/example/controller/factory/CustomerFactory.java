package org.example.controller.factory;

import org.example.model.dto.account.UserDto;
import org.example.model.service.CustomerManager;
import org.springframework.stereotype.Component;

@Component
class CustomerFactory implements UserFactory {

    private final CustomerManager customerManager;

    public CustomerFactory(CustomerManager customerManager) {
        this.customerManager = customerManager;
    }

    @Override
    public UserDto createUser() {
        return customerManager.getEmptyCustomer();
    }
}

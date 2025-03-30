package org.example.controller.factory;

import org.example.model.dto.account.UserDto;
import org.example.model.service.CustomerManager;

class CustomerFactory implements UserFactory {
    @Override
    public UserDto createUser() {
        return CustomerManager.getInstance().getEmptyCustomer();
    }
}

package org.example.model.entity.factory;

import org.example.model.service.CustomerManager;
import org.example.model.entity.UserEntity;

class CustomerFactory implements UserFactory {
    @Override
    public UserEntity createUser() {
        return CustomerManager.getInstance().buildCustomer();
    }
}

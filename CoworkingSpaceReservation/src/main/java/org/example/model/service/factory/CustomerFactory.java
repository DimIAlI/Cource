package org.example.model.service.factory;

import org.example.model.service.CustomerManager;
import org.example.model.entity.User;

class CustomerFactory implements UserFactory {
    @Override
    public User createUser() {
        return CustomerManager.getInstance().buildCustomer();
    }
}

package org.example.model.factory;

import org.example.model.CustomerManager;
import org.example.model.User;

class CustomerFactory implements UserFactory {
    @Override
    public User createUser() {
        return CustomerManager.getInstance().buildCustomer();
    }
}

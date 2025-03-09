package org.example.model.factory;


import org.example.model.AdminManager;
import org.example.model.User;

class AdminFactory implements UserFactory {
    @Override
    public User createUser() {
        return AdminManager.getInstance().buildAdmin();
    }
}


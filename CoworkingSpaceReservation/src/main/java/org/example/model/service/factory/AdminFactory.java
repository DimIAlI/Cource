package org.example.model.service.factory;


import org.example.model.service.AdminManager;
import org.example.model.entity.User;

class AdminFactory implements UserFactory {
    @Override
    public User createUser() {
        return AdminManager.getInstance().buildAdmin();
    }
}


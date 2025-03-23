package org.example.model.entity.factory;


import org.example.model.service.AdminManager;
import org.example.model.entity.UserEntity;

class AdminFactory implements UserFactory {
    @Override
    public UserEntity createUser() {
        return AdminManager.getInstance().buildAdmin();
    }
}


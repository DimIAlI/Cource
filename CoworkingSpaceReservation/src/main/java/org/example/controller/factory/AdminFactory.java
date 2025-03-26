package org.example.controller.factory;


import org.example.model.dto.UserDto;
import org.example.model.service.AdminManager;

class AdminFactory implements UserFactory {
    @Override
    public UserDto createUser() {
        return AdminManager.getInstance().buildAdmin();
    }
}


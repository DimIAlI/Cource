package org.example.controller.factory;


import org.example.model.dto.account.UserDto;
import org.example.model.service.AdminManager;
import org.springframework.stereotype.Component;

@Component
class AdminFactory implements UserFactory {
    private final AdminManager adminManager;

    AdminFactory(AdminManager adminManager) {
        this.adminManager = adminManager;
    }

    @Override
    public UserDto createUser() {
        return adminManager.getEmptyAdmin();
    }
}


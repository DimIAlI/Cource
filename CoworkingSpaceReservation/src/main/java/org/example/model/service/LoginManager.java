package org.example.model.service;


import org.example.model.entity.AdminEntity;
import org.example.model.entity.CustomerEntity;
import org.example.model.entity.UserEntity;
import org.example.model.storage.ApplicationStateManager;

import java.util.Map;

class LoginManager {
    private static final LoginManager INSTANCE = new LoginManager();

    static LoginManager getInstance() {
        return INSTANCE;
    }

    private final Map<String, CustomerEntity> registeredCustomers;
    private final Map<String, AdminEntity> registeredAdmins;

    private LoginManager() {
        registeredCustomers = ApplicationStateManager.getInstance().getState().getRegisteredCustomers();
        registeredAdmins = ApplicationStateManager.getInstance().getState().getRegisteredAdmins();
    }

    UserEntity authenticateOrRegister(UserEntity user, String login) {

            if (user instanceof AdminEntity) {
                return registeredAdmins.computeIfAbsent(login, k -> {
                    UserEntity editedUser = AdminManager.getInstance().setAdminLogin(user, login);
                    return (AdminEntity) editedUser;
                });
            } else {
                return registeredCustomers.computeIfAbsent(login, k -> {
                    UserEntity editedUser = CustomerManager.getInstance().setCustomerLogin(user, login);
                    return (CustomerEntity) editedUser;
                });
            }
    }
}

package org.example.model;


import org.example.model.storage.ApplicationStateManager;

import java.util.Map;

class LoginManager {
    private static final LoginManager INSTANCE = new LoginManager();

    static LoginManager getInstance() {
        return INSTANCE;
    }

    private final Map<String, Customer> registeredCustomers;
    private final Map<String, Admin> registeredAdmins;

    private LoginManager() {
        registeredCustomers = ApplicationStateManager.getInstance().getState().getRegisteredCustomers();
        registeredAdmins = ApplicationStateManager.getInstance().getState().getRegisteredAdmins();
    }

    User authenticateOrRegister(User user, String login) {

            if (user instanceof Admin) {
                return registeredAdmins.computeIfAbsent(login, k -> {
                    User editedUser = AdminManager.getInstance().setAdminLogin(user, login);
                    return (Admin) editedUser;
                });
            } else {
                return registeredCustomers.computeIfAbsent(login, k -> {
                    User editedUser = CustomerManager.getInstance().setCustomerLogin(user, login);
                    return (Customer) editedUser;
                });
            }
    }
}

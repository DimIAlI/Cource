package org.example.model;


import org.example.model.storage.ApplicationStateManager;

import java.util.Map;

public class LoginManager {
    private static final LoginManager INSTANCE = new LoginManager();

    public static LoginManager getInstance() {
        return INSTANCE;
    }

    private final Map<String, Customer> registeredCustomers;
    private final Map<String, Admin> registeredAdmins;

    private LoginManager() {
        registeredCustomers = ApplicationStateManager.getInstance().getState().getRegisteredCustomers();
        registeredAdmins = ApplicationStateManager.getInstance().getState().getRegisteredAdmins();
    }

    public User authenticateOrRegister(User user, String login) {
        //todo Tested and replaced with equivalent code.

//            if (user instanceof Admin) {
//                return registeredAdmins.computeIfAbsent(login, k -> {
//                    User editedUser = AdminManager.getInstance().setAdminLogin(user, login);
//                    return (Admin) editedUser;
//                });
//            } else {
//                return registeredCustomers.computeIfAbsent(login, k -> {
//                    User editedUser = CustomerManager.getInstance().setCustomerLogin(user, login);
//                    return (Customer) editedUser;
//                });
//            }

        User editedUser;
        if (user instanceof Admin) {
            if (registeredAdmins.containsKey(login)) {
                return registeredAdmins.get(login);
            } else {
                editedUser = AdminManager.getInstance().setAdminLogin(user, login);
                registeredAdmins.put(login, (Admin) editedUser);
                return editedUser;
            }
        } else {
            if (registeredCustomers.containsKey(login)) {
                return registeredCustomers.get(login);
            } else {
                editedUser = CustomerManager.getInstance().setCustomerLogin(user, login);
                registeredCustomers.put(login, (Customer) editedUser);
                return editedUser;
            }
        }
    }
}

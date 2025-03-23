package org.example.model.service;

import org.example.model.entity.AdminEntity;
import org.example.model.entity.UserEntity;

public class AdminManager {

    private static final AdminManager INSTANCE = new AdminManager();

    private AdminManager() {
    }

    public static AdminManager getInstance() {
        return INSTANCE;
    }

    public UserEntity getAdmin(UserEntity user, String login) {
        return LoginManager.getInstance().authenticateOrRegister(user, login);
    }

   public UserEntity setAdminLogin(UserEntity user, String login) {
        user.setLogin(login);
        return user;
    }

    public AdminEntity buildAdmin() {
        return AdminEntity.builder().build();
    }
}

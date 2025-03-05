package org.example.model;

public class AdminManager {

    private static final AdminManager INSTANCE = new AdminManager();

    private AdminManager() {
    }

    public static AdminManager getInstance() {
        return INSTANCE;
    }

    public User getAdmin(User user, String login) {
        return LoginManager.getInstance().authenticateOrRegister(user, login);
    }

   public User setAdminLogin(User user, String login) {
        user.setLogin(login);
        return user;
    }

    public Admin buildAdmin() {
        return Admin.builder().build();
    }
}

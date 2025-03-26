package org.example.model.service;

import org.example.model.dao.AdminDao;
import org.example.model.dao.CustomerDao;
import org.example.model.dto.AdminDto;
import org.example.model.dto.CustomerDto;
import org.example.model.dto.UserDto;
import org.example.model.entity.AdminEntity;
import org.example.model.entity.CustomerEntity;

import java.util.Optional;

class LoginManager {
    private static final LoginManager INSTANCE = new LoginManager();

    private LoginManager() {
    }

    static LoginManager getInstance() {
        return INSTANCE;
    }

    UserDto authenticateOrRegister(UserDto user, String login) {

        if (user instanceof AdminDto) {
            AdminDao adminDao = AdminDao.getInstance();

            Optional<AdminEntity> maybeUser = adminDao.findByLogin(login);
            if (maybeUser.isEmpty()) {
                AdminEntity newAdmin = AdminEntity.builder().login(login).build();
                AdminEntity savedAdmin = adminDao.save(newAdmin);
                return AdminDto.builder()
                        .id(savedAdmin.getId())
                        .login(savedAdmin.getLogin())
                        .build();
            }
            AdminEntity adminEntity = maybeUser.get();
            return AdminDto.builder()
                    .id(adminEntity.getId())
                    .login(adminEntity.getLogin())
                    .build();
        }

        CustomerDao customerDao = CustomerDao.getInstance();

        Optional<CustomerEntity> maybeUser = customerDao.findByLogin(login);
        if (maybeUser.isEmpty()) {
            CustomerEntity newCustomer = CustomerEntity.builder().login(login).build();
            CustomerEntity savedCustomer = customerDao.save(newCustomer);
            return CustomerDto.builder()
                    .id(savedCustomer.getId())
                    .login(savedCustomer.getLogin())
                    .build();
        }

        CustomerEntity customerEntity = maybeUser.get();
        return CustomerDto.builder()
                .id(customerEntity.getId())
                .login(customerEntity.getLogin())
                .build();
    }
}

package org.example.model.service;

import org.example.model.dto.account.AdminDto;
import org.example.model.dto.account.CustomerDto;
import org.example.model.dto.account.UserDto;
import org.example.model.dto.filters.account.AdminFilter;
import org.example.model.dto.filters.account.CustomerFilter;
import org.example.model.entity.account.AdminEntity;
import org.example.model.entity.account.CustomerEntity;
import org.example.model.repository.account.AdminRepository;
import org.example.model.repository.account.CustomerRepository;
import org.example.model.util.SessionManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

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

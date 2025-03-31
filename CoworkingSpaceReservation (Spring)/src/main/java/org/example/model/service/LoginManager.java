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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class LoginManager {
    private final SessionFactory sessionFactory;

    public LoginManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    UserDto authenticateOrRegister(UserDto user, String login) {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        if (user instanceof AdminDto) {
            return authenticateOrRegisterAdmin(login, session, transaction);
        } else
            return authenticateOrRegisterCustomer(login, session, transaction);
    }

    private AdminDto authenticateOrRegisterAdmin(String login, Session session, Transaction transaction) {
        AdminRepository repository = new AdminRepository(session);

        AdminFilter loginFilter = AdminFilter.builder()
                .login(login)
                .build();

        List<AdminEntity> maybeUser = repository.findAll(loginFilter);

        if (maybeUser.isEmpty()) {
            AdminEntity newAdmin = buildAdminEntity(login);
            AdminEntity savedAdmin = repository.save(newAdmin);

            transaction.commit();

            return buildAdminDto(savedAdmin);
        }
        transaction.commit();

        AdminEntity adminEntity = maybeUser.get(0);
        return buildAdminDto(adminEntity);
    }

    private CustomerDto authenticateOrRegisterCustomer(String login, Session session, Transaction transaction) {
        CustomerRepository repository = new CustomerRepository(session);

        CustomerFilter loginFilter = CustomerFilter.builder()
                .login(login)
                .build();

        List<CustomerEntity> maybeUser = repository.findAll(loginFilter);

        if (maybeUser.isEmpty()) {
            CustomerEntity newCustomer = buildCustomerEntity(login);
            CustomerEntity savedCustomer = repository.save(newCustomer);

            transaction.commit();
            return buildCustomerDto(savedCustomer);
        }
        transaction.commit();

        CustomerEntity customerEntity = maybeUser.get(0);
        return buildCustomerDto(customerEntity);
    }

    private AdminDto buildAdminDto(AdminEntity savedAdmin) {
        return AdminDto.builder()
                .id(savedAdmin.getId())
                .login(savedAdmin.getLogin())
                .build();
    }

    private CustomerDto buildCustomerDto(CustomerEntity savedCustomer) {
        return CustomerDto.builder()
                .id(savedCustomer.getId())
                .login(savedCustomer.getLogin())
                .build();
    }

    private AdminEntity buildAdminEntity(String login) {
        return AdminEntity.builder()
                .login(login)
                .build();
    }

    private CustomerEntity buildCustomerEntity(String login) {
        return CustomerEntity.builder()
                .login(login)
                .build();
    }
}

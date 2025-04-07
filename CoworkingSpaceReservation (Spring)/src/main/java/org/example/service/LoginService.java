package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.service.account.AdminDto;
import org.example.dto.service.account.CustomerDto;
import org.example.dto.service.account.UserDto;
import org.example.dto.view.ReadUserDto;
import org.example.dto.view.Role;
import org.example.entity.account.AdminEntity;
import org.example.entity.account.CustomerEntity;
import org.example.repository.account.AdminRepository;
import org.example.repository.account.CustomerRepository;
import org.example.service.filters.account.AdminFilter;
import org.example.service.filters.account.CustomerFilter;
import org.example.service.mapper.EntityConverter;
import org.example.service.mapper.entytyToDto.AdminMapper;
import org.example.service.mapper.entytyToDto.CustomerMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final SessionFactory sessionFactory;
    private final EntityConverter entityBuilder;
    private final AdminMapper adminMapper;
    private final CustomerMapper customerMapper;


    public UserDto authenticateOrRegister(ReadUserDto user) {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        if (user.getRole() == Role.ADMIN) {
            return authenticateOrRegisterAdmin(user, session, transaction);
        } else
            return authenticateOrRegisterCustomer(user, session, transaction);
    }

    private AdminDto authenticateOrRegisterAdmin(ReadUserDto user, Session session, Transaction transaction) {
        AdminRepository repository = new AdminRepository(session);

        AdminFilter loginFilter = AdminFilter.builder()
                .login(user.getLogin())
                .build();

        List<AdminEntity> maybeUser = repository.findAll(loginFilter);

        if (maybeUser.isEmpty()) {
            AdminEntity newAdmin = entityBuilder.convertToAdminEntity(user.getLogin());
            AdminEntity savedAdmin = repository.save(newAdmin);

            transaction.commit();

            return adminMapper.mapTo(savedAdmin);
        }
        transaction.commit();

        AdminEntity adminEntity = maybeUser.get(0);
        return adminMapper.mapTo(adminEntity);
    }

    private CustomerDto authenticateOrRegisterCustomer(ReadUserDto user, Session session, Transaction transaction) {
        CustomerRepository repository = new CustomerRepository(session);

        CustomerFilter loginFilter = CustomerFilter.builder()
                .login(user.getLogin())
                .build();

        List<CustomerEntity> maybeUser = repository.findAll(loginFilter);

        if (maybeUser.isEmpty()) {
            CustomerEntity newCustomer = entityBuilder.convertToCustomerEntity(user.getLogin());
            CustomerEntity savedCustomer = repository.save(newCustomer);

            transaction.commit();
            return customerMapper.mapTo(savedCustomer);
        }
        transaction.commit();

        CustomerEntity customerEntity = maybeUser.get(0);
        return customerMapper.mapTo(customerEntity);
    }
}

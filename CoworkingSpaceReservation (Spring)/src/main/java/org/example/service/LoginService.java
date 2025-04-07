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
import org.example.service.mapper.EntityConverter;
import org.example.service.mapper.entytyToDto.AdminMapper;
import org.example.service.mapper.entytyToDto.CustomerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final EntityConverter entityBuilder;
    private final AdminMapper adminMapper;
    private final CustomerMapper customerMapper;

    @Transactional
    public UserDto authenticateOrRegister(ReadUserDto user) {
        if (user.getRole() == Role.ADMIN) {
            return authenticateOrRegisterAdmin(user);
        } else {
            return authenticateOrRegisterCustomer(user);
        }
    }

    private AdminDto authenticateOrRegisterAdmin(ReadUserDto user) {

        String login = user.getLogin();
        Optional<AdminEntity> maybeUser = adminRepository.findByLogin(login);

        return maybeUser
                .map(adminMapper::mapTo)
                .orElseGet(() -> {

                    AdminEntity newAdmin = entityBuilder.convertToAdminEntity(login);
                    AdminEntity savedAdmin = adminRepository.save(newAdmin);

                    return adminMapper.mapTo(savedAdmin);
                });
    }

    private CustomerDto authenticateOrRegisterCustomer(ReadUserDto user) {

        String login = user.getLogin();
        Optional<CustomerEntity> maybeUser = customerRepository.findByLogin(login);

        return maybeUser.map(customerMapper::mapTo)
                .orElseGet(() -> {

                    CustomerEntity newCustomer = entityBuilder.convertToCustomerEntity(login);
                    CustomerEntity savedCustomer = customerRepository.save(newCustomer);

                    return customerMapper.mapTo(savedCustomer);
                });
    }
}

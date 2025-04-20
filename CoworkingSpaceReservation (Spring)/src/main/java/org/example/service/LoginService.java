package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.view.CustomUserDetails;
import org.example.dto.view.ReadUserDto;
import org.example.entity.account.*;
import org.example.exception.account.UserAlreadyExistsException;
import org.example.repository.account.AdminRepository;
import org.example.repository.account.CustomerRepository;
import org.example.repository.account.UserRepository;
import org.example.service.mapper.EntityConverter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final UserRepository<UserEntity> userRepository;
    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final EntityConverter converter;
    private final PasswordEncoder encoder;

    @Transactional
    public void createUser(ReadUserDto user) {

        String encodedPassword = encodePassword(user.getPassword());
        user.setPassword(encodedPassword);

        String login = user.getLogin();
        Optional<UserEntity> maybeUser = userRepository.findByLogin(login);

        maybeUser.ifPresentOrElse(u -> {
            throw new UserAlreadyExistsException(login);
        }, () -> {
            if (user.getRole() == Role.ADMIN) {
                createAdmin(user);
            } else if (user.getRole() == Role.CUSTOMER)
                createCustomer(user);
        });
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByLogin(username)
                .map(user -> new CustomUserDetails(
                        user.getId(),
                        user.getLogin(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())))
                .orElseThrow(() -> new UsernameNotFoundException("User with this login was not found"));
    }

    private void createAdmin(ReadUserDto user) {

        AdminEntity adminEntity = converter.convertToAdminEntity(user);
        adminRepository.save(adminEntity);
    }

    private void createCustomer(ReadUserDto user) {

        CustomerEntity customerEntity = converter.convertToCustomerEntity(user);
        customerRepository.save(customerEntity);
    }

    private String encodePassword(String password) {
        return encoder.encode(password);
    }
}

package org.example.controller.factory;

import jakarta.annotation.PostConstruct;
import org.example.model.dto.account.UserDto;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class UserFactorySelector {
    private Map<String, UserFactory> userFactories;
    private final AdminFactory adminFactory;
    private final CustomerFactory customerFactory;

    public UserFactorySelector(AdminFactory adminFactory, CustomerFactory customerFactory) {
        this.adminFactory = adminFactory;
        this.customerFactory = customerFactory;
    }
    public Optional<UserDto> getEmptyUser(String choice) {
        return Optional.ofNullable(userFactories.get(choice))
                .map(UserFactory::createUser);
    }
    @PostConstruct
    private void initFactory() {
        userFactories = Map.of(
                "1", adminFactory,
                "2", customerFactory
        );
    }
}

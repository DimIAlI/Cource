package org.example.controller.factory;

import org.example.model.dto.account.UserDto;

import java.util.Map;
import java.util.Optional;

public class UserFactorySelector {
    private final Map<String, UserFactory> userFactories;

    {
        userFactories = Map.of("1", new AdminFactory(),
                "2", new CustomerFactory());
    }

    public Optional<UserDto> getEmptyUser(String choice) {
        UserFactory factory = userFactories.get(choice);
        if (factory == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(factory.createUser());
    }
}

package org.example.dto.service.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@RequiredArgsConstructor
@Getter
@Setter
public abstract class UserDto {
    private final Long id;
    private final String login;
}

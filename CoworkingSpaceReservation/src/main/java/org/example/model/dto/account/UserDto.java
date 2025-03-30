package org.example.model.dto.account;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@RequiredArgsConstructor
@Getter
@Setter
public abstract class UserDto {
    private final Long id;
    private final String login;
}

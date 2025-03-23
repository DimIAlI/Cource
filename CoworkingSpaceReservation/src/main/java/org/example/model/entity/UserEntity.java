package org.example.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class UserEntity {
    private long id;
    private String login;
}

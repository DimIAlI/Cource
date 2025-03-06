package org.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
public abstract class User {
    @JsonProperty("login")
    private String login;
    @JsonCreator
    User(String login) {
        this.login = login;
    }
}

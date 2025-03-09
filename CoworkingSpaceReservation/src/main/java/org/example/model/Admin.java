package org.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Admin extends User {
    @JsonCreator
    Admin(@JsonProperty("login") String login) {
        super(login);
    }
}

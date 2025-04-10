package org.example.exception.account;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends RuntimeException {

    private final String login;

    public UserAlreadyExistsException(String login) {
        this.login = login;
    }
}

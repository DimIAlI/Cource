package org.example.exceptions;

import lombok.Getter;

@Getter
public class IdNotFoundException extends RuntimeException {
    private long id;

    public IdNotFoundException(long id) {
        this.id = id;
    }
}

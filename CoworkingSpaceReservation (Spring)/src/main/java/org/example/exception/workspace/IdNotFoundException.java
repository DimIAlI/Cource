package org.example.exception.workspace;

import lombok.Getter;

@Getter
public class IdNotFoundException extends RuntimeException {
    private final long id;

    public IdNotFoundException(long id) {
        this.id = id;
    }
}

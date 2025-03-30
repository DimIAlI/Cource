package org.example.exceptions;

import lombok.Getter;

@Getter
public class ReservationException extends RuntimeException{
    private long id;

    public ReservationException(long id) {
        this.id = id;
    }
}

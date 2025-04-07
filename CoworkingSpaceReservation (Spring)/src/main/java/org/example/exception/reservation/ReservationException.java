package org.example.exception.reservation;

import lombok.Getter;

@Getter
public class ReservationException extends RuntimeException{
    private final long id;

    public ReservationException(long id) {
        this.id = id;
    }
}

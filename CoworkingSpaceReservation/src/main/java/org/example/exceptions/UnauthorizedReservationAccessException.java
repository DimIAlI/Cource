package org.example.exceptions;

import lombok.Getter;

@Getter
public class UnauthorizedReservationAccessException extends ReservationException {

    public UnauthorizedReservationAccessException(long id) {
        super(id);
    }
}

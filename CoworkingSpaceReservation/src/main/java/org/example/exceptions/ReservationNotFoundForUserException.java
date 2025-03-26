package org.example.exceptions;

import lombok.Getter;

@Getter
public class ReservationNotFoundForUserException extends ReservationException {

    public ReservationNotFoundForUserException(long id) {
        super(id);
    }
}

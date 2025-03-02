package org.example.exceptions;

import lombok.Getter;

@Getter
public class ReservationDoesNotExistException extends ReservationException {
    public ReservationDoesNotExistException(long id) {
        super(id);
    }
}

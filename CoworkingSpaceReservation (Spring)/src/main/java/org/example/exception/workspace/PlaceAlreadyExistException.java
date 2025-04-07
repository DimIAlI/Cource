package org.example.exception.workspace;

import lombok.Getter;


@Getter
public class PlaceAlreadyExistException extends RuntimeException {
    private final String type;
    private final Double price;

    public PlaceAlreadyExistException(String type, Double price) {
        this.type = type;
        this.price = price;
    }
}

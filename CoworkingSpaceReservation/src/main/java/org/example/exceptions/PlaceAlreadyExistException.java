package org.example.exceptions;

import lombok.Getter;


@Getter
public class PlaceAlreadyExistException extends RuntimeException {
    private String type;
    private String price;

    public PlaceAlreadyExistException(String type, String price) {
        this.type = type;
        this.price = price;
    }
}

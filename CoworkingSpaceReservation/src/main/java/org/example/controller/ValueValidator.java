package org.example.controller;

import lombok.experimental.UtilityClass;
import org.example.model.entity.Admin;
import org.example.model.entity.SpaceType;
import org.example.model.entity.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

@UtilityClass
public class ValueValidator {
    private static final int MIN_ALLOWED_VALUE = 1;
    private static final int MAX_ALLOWED_FOR_MAIN_MENU = 3;
    private static final int MAX_ALLOWED_FOR_CUSTOMER = 5;
    private static final int MAX_ALLOWED_FOR_ADMIN = 4;

    static boolean checkValue(String message) {

        if (message == null || message.length() != 1) return false;

        try {
            int number = Integer.parseInt(message);
            return number >= MIN_ALLOWED_VALUE && number <= MAX_ALLOWED_FOR_MAIN_MENU;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean checkValue(User currentUser, String message) {
        if (message == null || message.length() != 1) return false;

        try {
            int number = Integer.parseInt(message);
            int maxAllowed = (currentUser instanceof Admin) ? MAX_ALLOWED_FOR_ADMIN : MAX_ALLOWED_FOR_CUSTOMER;

            return number >= MIN_ALLOWED_VALUE && number <= maxAllowed;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean checkPrice(String message) {
        try {
            return Double.parseDouble(message) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean checkType(String message) {
        if (message == null || message.isEmpty()) {
            return false;
        }
        return Arrays.stream(SpaceType.values())
                .anyMatch(type -> type.getDisplayName().equalsIgnoreCase(message));
    }

    public static boolean checkIdValue(String message) {
        if (message == null || message.isEmpty()) return false;
        try {
            Long.parseLong(message);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean checkDataType(String message, DateTimeFormatter formatter) {

        try {
            LocalDateTime inputDateTime = LocalDateTime.parse(message, formatter);

            if (isBeforeCurrentTime(inputDateTime)) {
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private static boolean isBeforeCurrentTime(LocalDateTime inputDateTime) {

        LocalDateTime currentDateTime = LocalDateTime.now();
        return inputDateTime.isBefore(currentDateTime);
    }

    public static boolean checkDataTypeAndRange(LocalDateTime startTime, String message, DateTimeFormatter formatter) {
        try {
            LocalDateTime parsedDate = LocalDateTime.parse(message, formatter);
            return parsedDate.isAfter(startTime);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean checkLogin(String login) {
        if (login == null || login.length() < 5) {
            return false;
        }
        return login.matches("[a-z0-9]+");
    }
}


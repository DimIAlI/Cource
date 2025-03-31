package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.properties")
public class CustomerViewConfig {

    @Bean
    public Map<String, String> customerMessages(
            @Value("${customer.welcome}") String welcome,
            @Value("${customer.enter.username}") String enterUsername,
            @Value("${customer.menu}") String customerMenu,
            @Value("${customer.menu.option1}") String menuOption1,
            @Value("${customer.menu.option2}") String menuOption2,
            @Value("${customer.menu.option3}") String menuOption3,
            @Value("${customer.menu.option4}") String menuOption4,
            @Value("${customer.menu.option5}") String menuOption5,
            @Value("${customer.browse.spaces}") String browseSpaces,
            @Value("${customer.make.reservation}") String makeReservation,
            @Value("${customer.view.reservations.menu}") String viewReservationsMenu,
            @Value("${customer.cancel.reservation}") String cancelReservation,
            @Value("${customer.available.spaces}") String availableSpaces,
            @Value("${customer.view.reservations}") String viewReservations,
            @Value("${customer.enter.reservation.id}") String enterReservationId,
            @Value("${customer.enter.start.date}") String enterStartDate,
            @Value("${customer.enter.end.date}") String enterEndDate,
            @Value("${customer.space.separator}") String spaceSeparator,
            @Value("${customer.space.format.table}") String spaceFormatTable,
            @Value("${customer.space.format}") String spaceFormat,
            @Value("${customer.reservation.separator}") String reservationSeparator,
            @Value("${customer.reservation.format.table}") String reservationFormatTable,
            @Value("${customer.reservation.format}") String reservationFormat,
            @Value("${customer.error.remove.reservation}") String errorRemoveReservation,
            @Value("${customer.error.reservations}") String errorReservations,
            @Value("${customer.error.reservation.exists}") String errorReservationExists,
            @Value("${customer.error.already.booked}") String errorAlreadyBooked,
            @Value("${customer.error.available.spaces}") String errorAvailableSpaces,

            @Value("${common.enter.choice}") String enterChoice,
            @Value("${common.login.rules}") String loginRules,
            @Value("${common.date.format}") String dateFormat,
            @Value("${common.success.operation}") String successOperation,
            @Value("${common.enter.space.id}") String enterSpaceId) {

        HashMap<String, String> messages = new HashMap<>();

        messages.put("welcome", welcome);
        messages.put("enterUsername", enterUsername);
        messages.put("customerMenu", customerMenu);
        messages.put("menuOption1", menuOption1);
        messages.put("menuOption2", menuOption2);
        messages.put("menuOption3", menuOption3);
        messages.put("menuOption4", menuOption4);
        messages.put("menuOption5", menuOption5);
        messages.put("browseSpaces", browseSpaces);
        messages.put("makeReservation", makeReservation);
        messages.put("viewReservationsMenu", viewReservationsMenu);
        messages.put("cancelReservation", cancelReservation);
        messages.put("availableSpaces", availableSpaces);
        messages.put("viewReservations", viewReservations);
        messages.put("enterReservationId", enterReservationId);
        messages.put("enterStartDate", enterStartDate);
        messages.put("enterEndDate", enterEndDate);
        messages.put("spaceSeparator", spaceSeparator);
        messages.put("spaceFormatTable", spaceFormatTable);
        messages.put("spaceFormat", spaceFormat);
        messages.put("reservationSeparator", reservationSeparator);
        messages.put("reservationFormatTable", reservationFormatTable);
        messages.put("reservationFormat", reservationFormat);
        messages.put("errorRemoveReservation", errorRemoveReservation);
        messages.put("errorReservations", errorReservations);
        messages.put("errorReservationExists", errorReservationExists);
        messages.put("errorAlreadyBooked", errorAlreadyBooked);
        messages.put("errorAvailableSpaces", errorAvailableSpaces);

        messages.put("enterChoice", enterChoice);
        messages.put("loginRules", loginRules);
        messages.put("dateFormat", dateFormat);
        messages.put("successOperation", successOperation);
        messages.put("enterSpaceId", enterSpaceId);

        return messages;
    }
}

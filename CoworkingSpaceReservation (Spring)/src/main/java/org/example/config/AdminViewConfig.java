package org.example.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.properties")
public class AdminViewConfig {

    @Bean
    public Map<String, String> adminMessages(
            @Value("${admin.welcome}") String welcome,
            @Value("${admin.enter.username}") String enterUsername,
            @Value("${admin.menu}") String adminMenu,
            @Value("${admin.menu.option1}") String menuOption1,
            @Value("${admin.menu.option2}") String menuOption2,
            @Value("${admin.menu.option3}") String menuOption3,
            @Value("${admin.menu.option4}") String menuOption4,
            @Value("${admin.add.space}") String addSpace,
            @Value("${admin.remove.space}") String removeSpace,
            @Value("${admin.view.reservations}") String viewReservations,
            @Value("${admin.list.spaces}") String listSpaces,
            @Value("${admin.list.reservations}") String listReservations,
            @Value("${admin.enter.space.type}") String enterSpaceType,
            @Value("${admin.enter.price}") String enterPrice,
            @Value("${admin.space.separator}") String spaceSeparator,
            @Value("${admin.space.format.table}") String spaceFormatTable,
            @Value("${admin.space.format}") String spaceFormat,
            @Value("${admin.reservation.separator}") String reservationSeparator,
            @Value("${admin.reservation.format.table}") String reservationFormatTable,
            @Value("${admin.reservation.format}") String reservationFormat,
            @Value("${admin.error.spaces}") String errorSpaces,
            @Value("${admin.error.reservations}") String errorReservations,
            @Value("${admin.error.space.exists}") String errorSpaceExists,

            @Value("${common.enter.choice}") String enterChoice,
            @Value("${common.login.rules}") String loginRules,
            @Value("${common.date.format}") String dateFormat,
            @Value("${common.success.operation}") String successOperation,
            @Value("${common.enter.space.id}") String enterSpaceId){

        HashMap<String, String> messages = new HashMap<>();

        messages.put("welcome", welcome);
        messages.put("enterUsername", enterUsername);
        messages.put("adminMenu", adminMenu);
        messages.put("menuOption1", menuOption1);
        messages.put("menuOption2", menuOption2);
        messages.put("menuOption3", menuOption3);
        messages.put("menuOption4", menuOption4);
        messages.put("addSpace", addSpace);
        messages.put("removeSpace", removeSpace);
        messages.put("viewReservations", viewReservations);
        messages.put("listSpaces", listSpaces);
        messages.put("listReservations", listReservations);
        messages.put("enterSpaceType", enterSpaceType);
        messages.put("enterPrice", enterPrice);
        messages.put("spaceSeparator", spaceSeparator);
        messages.put("spaceFormatTable", spaceFormatTable);
        messages.put("spaceFormat", spaceFormat);
        messages.put("reservationSeparator", reservationSeparator);
        messages.put("reservationFormatTable", reservationFormatTable);
        messages.put("reservationFormat", reservationFormat);
        messages.put("errorSpaces", errorSpaces);
        messages.put("errorReservations", errorReservations);
        messages.put("errorSpaceExists", errorSpaceExists);

        messages.put("enterChoice", enterChoice);
        messages.put("loginRules", loginRules);
        messages.put("dateFormat", dateFormat);
        messages.put("successOperation", successOperation);
        messages.put("enterSpaceId", enterSpaceId);

        return messages;
    }
}

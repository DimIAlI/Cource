package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.properties")
public class GeneralViewConfig {

    @Bean
    public Map<String, String> generalMessages(
            @Value("${general.welcome}") String welcome,
            @Value("${general.select.choice}") String selectChoice,
            @Value("${general.menu.option1}") String menuOption1,
            @Value("${general.menu.option2}") String menuOption2,
            @Value("${general.menu.option3}") String menuOption3,
            @Value("${general.press.any.key}") String pressAnyKey,
            @Value("${general.error.value}") String errorValue,
            @Value("${general.error.login}") String errorLogin,
            @Value("${general.error.id}") String errorId,
            @Value("${general.exit.message}") String exitMessage,

            @Value("${common.enter.choice}") String enterChoice,
            @Value("${common.login.rules}") String loginRules,
            @Value("${common.date.format}") String dateFormat,
            @Value("${common.success.operation}") String successOperation,
            @Value("${common.enter.space.id}") String enterSpaceId) {

        HashMap<String, String> messages = new HashMap<>();

        messages.put("welcome", welcome);
        messages.put("selectChoice", selectChoice);
        messages.put("menuOption1", menuOption1);
        messages.put("menuOption2", menuOption2);
        messages.put("menuOption3", menuOption3);
        messages.put("pressAnyKey", pressAnyKey);
        messages.put("errorValue", errorValue);
        messages.put("errorLogin", errorLogin);
        messages.put("errorId", errorId);
        messages.put("exitMessage", exitMessage);
        messages.put("enterChoice", enterChoice);

        messages.put("loginRules", loginRules);
        messages.put("dateFormat", dateFormat);
        messages.put("successOperation", successOperation);
        messages.put("enterSpaceId", enterSpaceId);

        return messages;
    }
}

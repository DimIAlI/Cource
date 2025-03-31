package org.example.view;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GeneralView {
    private final Map<String, String> generalMessages;

    public GeneralView(@Qualifier("generalMessages") Map<String, String> generalMessages) {
        this.generalMessages = generalMessages;
    }

    public void printWelcomeMessage() {
        System.out.println(generalMessages.get("welcome"));
    }

    public void printMenu() {
        System.out.println(generalMessages.get("selectChoice"));
        System.out.println(generalMessages.get("menuOption1"));
        System.out.println(generalMessages.get("menuOption2"));
        System.out.println(generalMessages.get("menuOption3"));
    }

    public void printEnterChoiceMessage() {
        System.out.print(generalMessages.get("enterChoice"));
    }

    public void printPressAnySymbolMessage() {
        System.out.print(generalMessages.get("pressAnyKey"));
    }

    public void printErrorMessage() {
        System.out.print(generalMessages.get("errorValue"));
    }

    public void printErrorLoginMessage() {
        System.out.println(generalMessages.get("errorLogin"));
    }

    public void printErrorIdMessage(Long id) {
        System.out.printf(generalMessages.get("errorId"), id);
    }

    public void printExitMessage() {
        System.out.println(generalMessages.get("exitMessage"));
    }
}

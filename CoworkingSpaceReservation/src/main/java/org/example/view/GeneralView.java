package org.example.view;

import static org.example.util.PropertiesUtil.*;

public class GeneralView {
    public void printWelcomeMessage() {
        System.out.println(getValue("general.welcome"));
    }

    public void printMenu() {
        System.out.println(getValue("general.select.choice"));
        System.out.println(getValue("general.menu.option1"));
        System.out.println(getValue("general.menu.option2"));
        System.out.println(getValue("general.menu.option3"));
    }

    public void printEnterChoiceMessage() {
        System.out.print(getValue("common.enter.choice"));
    }

    public void printPressAnySymbolMessage() {
        System.out.print(getValue("general.press.any.key"));
    }

    public void printErrorMessage() {
        System.out.print(getValue("general.error.value"));
    }

    public void printErrorLoginMessage() {
        System.out.println(getValue("general.error.login"));
    }

    public void printErrorIdMessage(Long id) {
        System.out.printf(getValue("general.error.id"), id);
    }

    public void printExitMessage() {
        System.out.println(getValue("general.exit.message"));
    }
}

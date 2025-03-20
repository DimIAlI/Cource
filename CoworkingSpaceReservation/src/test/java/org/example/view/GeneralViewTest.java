package org.example.view;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class GeneralViewTest {

    private GeneralView generalView;
    private String expectedOutput;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalSystemOut = System.out;

    @BeforeEach
    void setUp() {
        generalView = new GeneralView();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    @DisplayName("Should display welcome message when printWelcomeMessage() is called")
    void printWelcomeMessage_shouldDisplayWelcomeMessage_whenMethodIsCalled() {

        //given
        expectedOutput = """
                                                
                =Welcome to the Coworking Space Reservation System!=
                """;

        //when
        generalView.printWelcomeMessage();

        //then
        assertThat(expectedOutput).isEqualTo(normalizeOutput(outputStream));
    }

    @Test
    @DisplayName("Should display selection menu when printMenu() is called")
    void printMenu_shouldDisplaySelectionMenu_whenMethodIsCalled() {

        //given
        expectedOutput = """
                                 
                Please select an option:
                1. Admin Login
                2. User Login
                3. Exit
                """;

        //when
        generalView.printMenu();

        //then
        assertThat(expectedOutput).isEqualTo(normalizeOutput(outputStream));
    }

    @Test
    @DisplayName("Should display enter choice message when printEnterChoiceMessage() is called")
    void printEnterChoiceMessage_shouldDisplayEnterChoiceMessage_whenMethodIsCalled() {

        //given
        expectedOutput = """
                                 
                Enter your choice:\s""";

        //when
        generalView.printEnterChoiceMessage();

        //then
        assertThat(expectedOutput).isEqualTo(normalizeOutput(outputStream));
    }

    @Test
    @DisplayName("Should display error message when printErrorMessage() is called")
    void printErrorMessage_shouldDisplayErrorMessage_whenMethodIsCalled() {

        //given
        expectedOutput = """
                                
                Error value, try to choose another value!
                """;

        //when
        generalView.printErrorMessage();

        //then
        assertThat(expectedOutput).isEqualTo(normalizeOutput(outputStream));
    }

    @Test
    @DisplayName("Should display exit message when printExitMessage() is called")
    void printExitMessage_shouldDisplayExitMessage_whenMethodIsCalled() {

        //given
        expectedOutput = """
                                
                Thank you for using the Coworking Space Reservation System! Goodbye!
                """;

        //when
        generalView.printExitMessage();

        //then
        assertThat(expectedOutput).isEqualTo(normalizeOutput(outputStream));
    }

    @Test
    @DisplayName("Should display 'press any key' message when printPressAnySymbolMessage() is called")
    void printPressAnySymbolMessage_shouldDisplayPressAnyKeyMessage_whenMethodIsCalled() {

        //given
        expectedOutput = """
                                
                Press any key to exit:""";

        //when
        generalView.printPressAnySymbolMessage();

        //then
        assertThat(expectedOutput).isEqualTo(normalizeOutput(outputStream));
    }

    @Test
    @DisplayName("Should display invalid login message when printErrorLoginMessage() is called")
    void printErrorLoginMessage_shouldDisplayInvalidLoginMessage_whenMethodIsCalled() {

        //given
        expectedOutput = """
                                
                Your login does not meet the validation requirements!
                """;

        //when
        generalView.printErrorLoginMessage();

        //then
        assertThat(expectedOutput).isEqualTo(normalizeOutput(outputStream));
    }

    @ParameterizedTest(name = "ID: {0}")
    @NullSource
    @MethodSource("provideRandomLongs")
    @DisplayName("Should display invalid ID message when printErrorIdMessage() is called with an invalid ID")
    void printErrorIdMessage_shouldDisplayInvalidIdMessage_whenInvalidIdIsProvided(Long id) {

        //given
        expectedOutput = """
                                
                Id %s is not valid!
                """.formatted(id);

        //when
        generalView.printErrorIdMessage(id);

        //then
        assertThat(expectedOutput).isEqualTo(normalizeOutput(outputStream));
    }

    @AfterEach
    void tearDown() {
        expectedOutput = null;
        System.setOut(originalSystemOut);
    }

    private static String normalizeOutput(ByteArrayOutputStream outputStream) {
        return outputStream.toString().replace("\r\n", "\n");
    }

    private static Stream<Long> provideRandomLongs() {
        Random random = new Random();
        return IntStream.range(0, 20)
                .mapToObj(i -> random.nextLong());
    }
}
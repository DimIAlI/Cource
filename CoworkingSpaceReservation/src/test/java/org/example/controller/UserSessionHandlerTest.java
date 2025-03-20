package org.example.controller;

import org.example.model.User;
import org.example.model.factory.UserFactorySelector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSessionHandlerTest {
    @Mock
    private GeneralController generalController;
    @Mock
    private UserFactorySelector userFactorySelector;
    @InjectMocks
    private UserSessionHandler userSessionHandler;

    private static final String VALID_CHOICE = "ValidChoice";
    private static final String INVALID_CHOICE = "InvalidChoice";
    private static final String EMPTY_CHOICE = "";
    private static final String NULL_CHOICE = null;
    private static final String VALID_LOGIN = "validLogin";
    private static final String INVALID_LOGIN = "invalidLogin";

    @Test
    @DisplayName("Should return the received message when the user provides a valid choice")
    void getUserChoice_shouldReturnReceivedMessage_whenChoiceIsValid() {

        //given
        try (MockedStatic<ValueValidator> valueValidatorMockedStatic = mockStatic(ValueValidator.class)) {

            given(ValueValidator.checkValue(anyString())).willReturn(true);
            given(generalController.getUserMessage()).willReturn(VALID_CHOICE);

            //when
            String actualChoice = userSessionHandler.getUserChoice();

            //then
            assertThat(actualChoice).isEqualTo(VALID_CHOICE);

            then(generalController).should().showEnterChoiceMessage();
            then(generalController).should().getUserMessage();
            then(generalController).should(never()).showErrorMessage();
        }
    }

    @Test
    @DisplayName("Should repeat getting message until a valid login is received when the message is invalid")
    void getUserChoice_shouldReturnValidMessage_afterMultipleInvalidAttempts() {

        //given
        try (MockedStatic<ValueValidator> valueValidatorMockedStatic = mockStatic(ValueValidator.class)) {

            given(ValueValidator.checkValue(INVALID_CHOICE)).willReturn(false);
            given(ValueValidator.checkValue(VALID_CHOICE)).willReturn(true);
            given(generalController.getUserMessage()).willReturn(INVALID_CHOICE, INVALID_CHOICE, INVALID_CHOICE, VALID_CHOICE);

            //when
            String actualChoice = userSessionHandler.getUserChoice();

            //then
            assertThat(actualChoice).isEqualTo(VALID_CHOICE);

            then(generalController).should(times(4)).showEnterChoiceMessage();
            then(generalController).should(times(4)).getUserMessage();
            then(generalController).should(times(3)).showErrorMessage();
        }
    }

    @Test
    @DisplayName("Should return an empty string when the user provides an empty choice")
    void getUserChoice_shouldReturnEmptyString_whenChoiceIsEmpty() {

        //given
        try (MockedStatic<ValueValidator> valueValidatorMockedStatic = mockStatic(ValueValidator.class)) {

            given(ValueValidator.checkValue(anyString())).willReturn(false, true);
            given(generalController.getUserMessage()).willReturn(EMPTY_CHOICE);

            // when
            String actualChoice = userSessionHandler.getUserChoice();

            // then
            assertThat(actualChoice).isEqualTo(EMPTY_CHOICE);

            then(generalController).should(times(2)).showEnterChoiceMessage();
            then(generalController).should(times(2)).getUserMessage();
            then(generalController).should().showErrorMessage();
        }
    }

    @Test
    @DisplayName("Should return null when the user provides null as a choice")
    void getUserChoice_shouldReturnNull_whenChoiceIsNull() {

        //given
        try (MockedStatic<ValueValidator> valueValidatorMockedStatic = mockStatic(ValueValidator.class)) {

            given(ValueValidator.checkValue(isNull())).willReturn(false, true);
            given(generalController.getUserMessage()).willReturn(NULL_CHOICE);

            // when
            String actualChoice = userSessionHandler.getUserChoice();

            // then
            assertThat(actualChoice).isNull();

            then(generalController).should(times(2)).showEnterChoiceMessage();
            then(generalController).should(times(2)).getUserMessage();
            then(generalController).should().showErrorMessage();
        }
    }

    @Test
    @DisplayName("Should return empty Optional when the choice is invalid")
    void getEmptyUser_shouldReturnEmptyOptional_whenChoiceIsInvalid() {

        // given
        given(userFactorySelector.getEmptyUser(anyString())).willReturn(Optional.empty());

        // when
        Optional<User> result = userSessionHandler.getEmptyUser(anyString());

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return non-empty Optional when the choice is valid")
    void getEmptyUser_shouldReturnNonEmptyOptional_whenChoiceIsValid() {

        // given
        User user = mock(User.class);
        given(userFactorySelector.getEmptyUser(anyString())).willReturn(Optional.of(user));

        // when
        Optional<User> result = userSessionHandler.getEmptyUser(anyString());

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(user);
    }

    @Test
    @DisplayName("Should return empty Optional when the choice is null")
    void getEmptyUser_shouldReturnEmptyOptional_whenChoiceIsNull() {

        // given
        given(userFactorySelector.getEmptyUser(isNull())).willReturn(Optional.empty());

        // when
        Optional<User> result = userSessionHandler.getEmptyUser(isNull());

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return the received login when the user provides a valid login")
    void getUserLogin_shouldReturnReceivedLogin_whenLoginIsValid() {

        // given
        String validLogin = "validLogin";
        try (MockedStatic<ValueValidator> valueValidatorMockedStatic = mockStatic(ValueValidator.class)) {

            given(ValueValidator.checkLogin(anyString())).willReturn(true);
            given(generalController.getUserMessage()).willReturn(validLogin);

            // when
            String actualLogin = userSessionHandler.getUserLogin(any());

            // then
            assertThat(actualLogin).isEqualTo(validLogin.toLowerCase());

            then(generalController).should().showWelcomeMessage(any());
            then(generalController).should().getUserMessage();
            then(generalController).should(never()).showErrorLoginMessage();
        }
    }

    @Test
    @DisplayName("Should repeat getting login until a valid login is received when the login is invalid")
    void getUserLogin_shouldRepeatGettingLogin_whenLoginIsInvalid() {

        // given
        String invalidLogin = "invalidLogin";
        try (MockedStatic<ValueValidator> valueValidatorMockedStatic = mockStatic(ValueValidator.class)) {

            given(ValueValidator.checkLogin(anyString())).willReturn(false, false, true);
            given(generalController.getUserMessage()).willReturn(invalidLogin);

            // when
            String actualLogin = userSessionHandler.getUserLogin(any());

            // then
            assertThat(actualLogin).isEqualTo(invalidLogin.toLowerCase());

            then(generalController).should(times(3)).showWelcomeMessage(any());
            then(generalController).should(times(3)).getUserMessage();
            then(generalController).should(times(2)).showErrorLoginMessage();
        }
    }

    @Test
    @DisplayName("Should return the received login when the user provides a valid login after multiple invalid attempts")
    void getUserLogin_shouldReturnValidLogin_afterMultipleInvalidAttempts() {

        // given
        try (MockedStatic<ValueValidator> valueValidatorMockedStatic = mockStatic(ValueValidator.class)) {

            given(ValueValidator.checkLogin(INVALID_LOGIN.toLowerCase())).willReturn(false);
            given(ValueValidator.checkLogin(VALID_LOGIN.toLowerCase())).willReturn(true);
            given(generalController.getUserMessage()).willReturn(INVALID_LOGIN, INVALID_LOGIN, VALID_LOGIN);

            // when
            String actualLogin = userSessionHandler.getUserLogin(any());

            // then
            assertThat(actualLogin).isEqualTo(VALID_LOGIN.toLowerCase());
            then(generalController).should(times(3)).showWelcomeMessage(any());
            then(generalController).should(times(3)).getUserMessage();
            then(generalController).should(times(2)).showErrorLoginMessage();
        }
    }
}

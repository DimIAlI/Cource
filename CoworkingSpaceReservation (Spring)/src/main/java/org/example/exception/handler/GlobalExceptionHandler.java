package org.example.exception.handler;

import lombok.RequiredArgsConstructor;
import org.example.controller.WorkspaceController;
import org.example.dto.view.*;
import org.example.exception.account.UserAlreadyExistsException;
import org.example.exception.reservation.NoReservationExistException;
import org.example.exception.reservation.ReservationNotFoundForUserException;
import org.example.exception.reservation.UserHasNoReservationsException;
import org.example.exception.reservation.WorkspaceAlreadyBookedAtTimeException;
import org.example.exception.workspace.IdNotFoundException;
import org.example.exception.workspace.NoAvailableSpacesException;
import org.example.exception.workspace.NoSpacesAddedException;
import org.example.exception.workspace.PlaceAlreadyExistException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final WorkspaceController workspaceController;

    @ExceptionHandler(NoReservationExistException.class)
    public String handeNoReservationExistException(Model model) {

        model.addAttribute("errorMessage", "No reservations found or added.");
        return "reservations/all-reservations-list";
    }

    @ExceptionHandler(UserHasNoReservationsException.class)
    public String handleUserHasNoReservationsException(Model model) {

        model.addAttribute("errorMessage", "You have no reservations");
        return "reservations/customer-reservations-list";
    }

    @ExceptionHandler(WorkspaceAlreadyBookedAtTimeException.class)
    public String handleWorkspaceAlreadyBookedAtTimeException(Model model) {

        model.addAttribute("errorMessage", "You cannot book this workspace for this period");
        model.addAttribute("addReservationDto", new AddReservationDto());
        return "reservations/add-form";
    }

    @ExceptionHandler(ReservationNotFoundForUserException.class)
    public String handleReservationNotFoundForUserException(Model model) {

        model.addAttribute("errorMessage", "You don't have a reservation with this ID.");
        model.addAttribute("deleteReservationDto", new DeleteReservationDto());
        return "reservations/remove-form";
    }

    @ExceptionHandler(NoAvailableSpacesException.class)
    public String handleNoAvailableSpacesException(Model model) {

        model.addAttribute("errorMessage", "No available spaces in the selected time range");
        model.addAttribute("availableDto", new AvailableSpaceDto());

        return "workspaces/available-form";
    }

    @ExceptionHandler(NoSpacesAddedException.class)
    public String handleNoSpacesAddedException(Model model) {

        workspaceController.setSpaceTypeAttributes(model, new AddWorkspaceDto());
        model.addAttribute("spacesErrorMessage", "No added workspaces was found");

        return "workspaces/add-form";
    }

    @ExceptionHandler(PlaceAlreadyExistException.class)
    public String handlePlaceAlreadyExistException(Model model) {

        workspaceController.setSpaceTypeAttributes(model, new AddWorkspaceDto());
        workspaceController.setAllSpacesAttribute(model);
        model.addAttribute("errorMessage", "This workspace already exists");

        return "workspaces/add-form";
    }

    @ExceptionHandler(IdNotFoundException.class)
    public String handleIdNotFoundException(Model model) {

        model.addAttribute("idErrorMessage", "No workspace found with this ID");
        model.addAttribute("deleteDto", new DeleteSpaceDto());
        workspaceController.setAllSpacesAttribute(model);

        return "workspaces/remove-form";
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExistsException(Model model) {
        model.addAttribute("userExistMessage", "User with this name already exists");
        model.addAttribute("userDto", new ReadUserDto());

        return "common/registration-page";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(){
        return "common/error-access-page";
    }

    @ExceptionHandler(Exception.class)
    public String handleGlobalExceptions (){
        return "common/error-page";
    }
}

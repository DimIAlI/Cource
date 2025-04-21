package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.controller.validation.Validator;
import org.example.dto.service.space.ReservationDto;
import org.example.dto.view.AddReservationDto;
import org.example.dto.view.DeleteReservationDto;
import org.example.dto.view.CustomUserDetails;
import org.example.service.ReservationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final Validator validator;

    @GetMapping("/admin/reservations")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getAdminChoice(@RequestParam(name = "action") String action,
                                 RedirectAttributes redirectAttributes) {

        return switch (action) {
            case "view" -> "redirect:/admin/reservations/all";
            default -> {
                redirectAttributes.addFlashAttribute("errorMessage", "Invalid action parameter for admin reservations.");
                yield "redirect:/dashboard";
            }
        };
    }

    @GetMapping("/user/reservations")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER')")
    public String getCustomerChoice(@RequestParam(name = "action") String action,
                                    RedirectAttributes redirectAttributes) {

        return switch (action) {
            case "view" -> "redirect:/user/reservations/list";
            case "add" -> "redirect:/user/reservations/add-reservation-form";
            case "remove" -> "redirect:/user/reservations/remove-form";
            default -> {
                redirectAttributes.addFlashAttribute("errorMessage", "Invalid action parameter for user reservations.");
                yield "redirect:/dashboard";
            }
        };
    }

    @GetMapping("/admin/reservations/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getAllSpaces(Model model) {

        List<ReservationDto> reservationList = reservationService.getAll();
        model.addAttribute("reservationList", reservationList);

        return "reservations/all-reservations-list";
    }

    @GetMapping("/user/reservations/list")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER')")
    public String getCustomerReservations(Model model,
                                          @AuthenticationPrincipal CustomUserDetails userDetails) {

        List<ReservationDto> customerReservations = reservationService.getCustomerReservations(userDetails);
        model.addAttribute("customerReservations", customerReservations);

        return "reservations/customer-reservations-list";
    }

    @GetMapping("/user/reservations/add-reservation-form")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER')")
    public String addReservation(Model model) {

        model.addAttribute("addReservationDto", new AddReservationDto());
        return "reservations/add-form";
    }

    @PostMapping("user/reservations")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER')")
    public String addReservation(@Valid @ModelAttribute("addReservationDto") AddReservationDto addReservationDto,
                                 BindingResult result,
                                 Model model,
                                 @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (!validator.isStartTimeBeforeEndTime(addReservationDto.getStartTime(), addReservationDto.getEndTime())) {
            result.rejectValue("startTime", "startTime.afterEndTime", "Start time must be before end time");
        }

        if (result.hasErrors()) {
            model.addAttribute("addReservationDto", addReservationDto);
            return "reservations/add-form";
        }

        addReservationDto.setCustomerId(userDetails.getId());
        reservationService.add(addReservationDto);

        return "common/success-page";
    }

    @GetMapping("/user/reservations/remove-form")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER')")
    public String removeReservation(Model model) {

        model.addAttribute("deleteReservationDto", new DeleteReservationDto());

        return "reservations/remove-form";
    }

    @DeleteMapping("user/reservations")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER')")
    public String removeReservation(@Valid @ModelAttribute("deleteReservationDto") DeleteReservationDto deleteReservationDto,
                                    BindingResult result,
                                    Model model,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (result.hasErrors()) {
            model.addAttribute("deleteReservationDto", deleteReservationDto);
            return "reservations/remove-form";
        }

        deleteReservationDto.setCustomerId(userDetails.getId());
        reservationService.remove(deleteReservationDto);

        return "common/success-page";
    }
}

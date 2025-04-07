package org.example.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.controller.validation.Validator;
import org.example.dto.service.account.CustomerDto;
import org.example.dto.service.space.ReservationDto;
import org.example.dto.view.AddReservationDto;
import org.example.dto.view.DeleteReservationDto;
import org.example.service.ReservationService;
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
    public String getAllSpaces(Model model) {

        List<ReservationDto> reservationList = reservationService.getAll();
        model.addAttribute("reservationList", reservationList);

        return "/reservations/all-reservations-list";
    }

    @GetMapping("/user/reservations/list")
    public String getCustomerReservations(Model model, HttpSession session) {

        CustomerDto currentUser = (CustomerDto) session.getAttribute("currentUser");
        List<ReservationDto> customerReservations = reservationService.getCustomerReservations(currentUser);
        model.addAttribute("customerReservations", customerReservations);

        return "reservations/customer-reservations-list";
    }

    @GetMapping("/user/reservations/add-reservation-form")
    public String addReservation(Model model) {

        model.addAttribute("addReservationDto", new AddReservationDto());
        return "reservations/add-form";
    }

    @PostMapping("user/reservations")
    public String addReservation(@Valid @ModelAttribute("addReservationDto") AddReservationDto addReservationDto,
                                 BindingResult result,
                                 Model model,
                                 HttpSession session) {

        if (!validator.isStartTimeBeforeEndTime(addReservationDto.getStartTime(), addReservationDto.getEndTime())) {
            result.rejectValue("startTime", "startTime.afterEndTime", "Start time must be before end time");
        }

        if (result.hasErrors()) {
            model.addAttribute("addReservationDto", addReservationDto);
            return "reservations/add-form";
        }

        CustomerDto currentUser = (CustomerDto) session.getAttribute("currentUser");
        addReservationDto.setCustomerId(currentUser.getId());

        reservationService.add(addReservationDto);

        return "common/success-page";
    }


    @GetMapping("/user/reservations/remove-form")
    public String removeReservation(Model model) {
        model.addAttribute("deleteReservationDto", new DeleteReservationDto());

        return "reservations/remove-form";
    }

    @DeleteMapping("user/reservations")
    public String removeReservation(@Valid @ModelAttribute("deleteReservationDto") DeleteReservationDto deleteReservationDto,
                                    BindingResult result,
                                    Model model,
                                    HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("deleteReservationDto", deleteReservationDto);
            return "reservations/remove-form";
        }

        CustomerDto currentUser = (CustomerDto) session.getAttribute("currentUser");
        deleteReservationDto.setCustomerId(currentUser.getId());

        reservationService.remove(deleteReservationDto);

        return "common/success-page";
    }
}

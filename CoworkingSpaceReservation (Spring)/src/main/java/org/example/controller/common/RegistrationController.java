package org.example.controller.common;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.view.ReadUserDto;
import org.example.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final LoginService loginService;

    @GetMapping
    public String registerUser(Model model) {
        model.addAttribute("userDto", new ReadUserDto());

        return "common/registration-page";
    }

    @PostMapping
    public String registerUser(@Valid @ModelAttribute("userDto") ReadUserDto userDto,
                               BindingResult result) {

        if (result.hasErrors()) {
            return "common/registration-page";
        }
        loginService.createUser(userDto);

        return "redirect:/login";
    }
}

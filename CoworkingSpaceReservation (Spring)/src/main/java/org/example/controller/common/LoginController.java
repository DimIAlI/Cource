package org.example.controller.common;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.example.dto.service.account.UserDto;
import org.example.dto.view.ReadUserDto;
import org.example.service.LoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final Integer sessionTimeout;
    private final LoginService loginService;

    public LoginController(@Value("${server.servlet.session.timeout}") Integer sessionTimeout,
                           LoginService loginService) {
        this.sessionTimeout = sessionTimeout;
        this.loginService = loginService;
    }

    @GetMapping
    public String getLoginPage(Model model) {

        model.addAttribute("userDto", new ReadUserDto());
        return "common/login-page";
    }

    @PostMapping
    public String getLoginForm(@Valid @ModelAttribute("userDto") ReadUserDto userDto,
                               BindingResult result,
                               HttpSession session) {

        if (result.hasErrors()) {
            return "common/login-page";
        }

        UserDto user = loginService.authenticateOrRegister(userDto);

        session.setAttribute("currentUser", user);
        session.setMaxInactiveInterval(sessionTimeout);

        return "redirect:/dashboard";
    }
}

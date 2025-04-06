package org.example.controller.common;

import jakarta.servlet.http.HttpSession;
import org.example.dto.service.account.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) {

        UserDto currentUser = (UserDto) session.getAttribute("currentUser");

        model.addAttribute("userLogin", currentUser.getLogin());
        model.addAttribute("role", currentUser.getClass().getSimpleName());

        return "common/dashboard";
    }
}


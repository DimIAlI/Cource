package org.example.controller.common;

import org.example.dto.view.CustomUserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;

@Controller
public class DashboardController {
    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or hasAnyRole('ROLE_CUSTOMER')")
    public String showDashboard(Model model,
                                @AuthenticationPrincipal CustomUserDetails userDetails) {

        String login = userDetails.getUsername();
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        model.addAttribute("userLogin", login);
        model.addAttribute("role", role);

        return "common/dashboard";
    }
}


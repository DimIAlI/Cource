package org.example.dto.view;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.entity.account.Role;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ReadUserDto {
    @NotNull(message = "Login cannot be empty")
    @Size(min = 5, max = 25, message = "Login must be between 5 and 25 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Login must contain only letters and numbers without special symbols")
    private String login;

    @NotNull(message = "Password cannot be empty")
    @Size(min = 10, max = 25, message = "Password must be between 10 and 25 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Password must contain only letters and numbers without special symbols")
    private String password;

    @NotNull(message = "Role cannot be empty")
    private Role role;
}

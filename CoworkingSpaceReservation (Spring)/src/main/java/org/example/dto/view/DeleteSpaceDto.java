package org.example.dto.view;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class DeleteSpaceDto {
    @NotNull(message = "Workspace ID cannot be null")
    @Min(value = 1, message = "Workspace ID must be greater than or equal to 1")
    private Long id;
}

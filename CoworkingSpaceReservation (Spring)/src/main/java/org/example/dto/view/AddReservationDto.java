package org.example.dto.view;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AddReservationDto {
    @NotNull(message = "ID cannot be null")
    @Min(value = 1, message = "ID must be greater than or equal to 1")
    private Long spaceId;
    @NotNull(message = "Start time cannot be null")
    @FutureOrPresent(message = "Start time must be in the present or future")
    private LocalDateTime startTime;
    @NotNull(message = "End time cannot be null")
    @FutureOrPresent(message = "End time must be in the present or future")
    private LocalDateTime endTime;

    private Long customerId;

}

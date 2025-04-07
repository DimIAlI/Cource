package org.example.dto.view;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteReservationDto {
    @NotNull(message = "Reservation ID cannot be null")
    @Min(value = 1, message = "Reservation ID must be greater than or equal to 1")
    private Long reservationId;

    private Long customerId;
}

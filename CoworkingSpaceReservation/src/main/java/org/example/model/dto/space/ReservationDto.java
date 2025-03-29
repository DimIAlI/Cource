package org.example.model.dto.space;

import lombok.*;
import org.example.model.dto.account.CustomerDto;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationDto {
    private Long id;
    private CustomerDto customer;
    private WorkspaceDto space;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

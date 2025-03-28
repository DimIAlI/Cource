package org.example.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationDto {
    private long id;
    private CustomerDto customer;
    private WorkspaceDto space;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

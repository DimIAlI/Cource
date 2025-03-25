package org.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private long id;
    private CustomerDto customer;
    private WorkspaceDto space;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

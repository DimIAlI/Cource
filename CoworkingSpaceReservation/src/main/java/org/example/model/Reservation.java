package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private long id;
    private Customer customer;
    private Workspace space;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

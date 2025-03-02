package org.example.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class Reservation {
    private long id;
    private Customer customer;
    private Workspace space;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

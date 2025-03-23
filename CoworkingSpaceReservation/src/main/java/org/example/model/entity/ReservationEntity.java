package org.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity {
    private long id;
    private CustomerEntity customer;
    private WorkspaceEntity space;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

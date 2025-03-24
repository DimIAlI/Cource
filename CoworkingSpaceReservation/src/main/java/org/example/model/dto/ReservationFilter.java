package org.example.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.model.entity.CustomerEntity;
import org.example.model.entity.WorkspaceEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReservationFilter {
    private long id;
    private CustomerEntity customer;
    private WorkspaceEntity space;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

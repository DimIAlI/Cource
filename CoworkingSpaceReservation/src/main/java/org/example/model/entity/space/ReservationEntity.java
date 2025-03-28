package org.example.model.entity.space;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.example.model.entity.BaseEntity;
import org.example.model.entity.accunt.CustomerEntity;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@SuperBuilder
public class ReservationEntity extends BaseEntity<Long> {
    private CustomerEntity customer;
    private WorkspaceEntity space;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

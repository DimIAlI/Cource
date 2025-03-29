package org.example.model.dto.space;

import lombok.*;
import org.example.model.dto.account.CustomerDto;

import java.time.LocalDateTime;

@Builder
@RequiredArgsConstructor
@Getter
@Setter
public class ReservationDto {
    private final Long id;
    private final CustomerDto customer;
    private final WorkspaceDto space;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
}

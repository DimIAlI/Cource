package org.example.dto.service.space;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@RequiredArgsConstructor
@Getter
@Setter
public class ReservationDto {
    private final Long id;
    private final String customerLogin;
    private final WorkspaceDto space;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
}

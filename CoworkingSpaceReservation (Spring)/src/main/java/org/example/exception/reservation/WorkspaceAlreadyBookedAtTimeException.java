package org.example.exception.reservation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class WorkspaceAlreadyBookedAtTimeException extends RuntimeException {

    private final Long spaceId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
}

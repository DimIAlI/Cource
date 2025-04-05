package org.example.exception.workspace;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class NoAvailableSpacesException extends RuntimeException {

    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

}

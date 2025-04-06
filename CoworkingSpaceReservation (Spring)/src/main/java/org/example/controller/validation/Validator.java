package org.example.controller.validation;

import lombok.RequiredArgsConstructor;
import org.example.service.SpaceTypeService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Validator {

    private final SpaceTypeService manager;

    public boolean isTypeAllowed(String type) {
        Set<String> allowedTypes = manager.getValues().keySet();
        return allowedTypes.stream().anyMatch(elem -> elem.equals(type));
    }

    public boolean isStartTimeBeforeEndTime(LocalDateTime startTime, LocalDateTime endTime) {
        return startTime.isBefore(endTime);
    }
}

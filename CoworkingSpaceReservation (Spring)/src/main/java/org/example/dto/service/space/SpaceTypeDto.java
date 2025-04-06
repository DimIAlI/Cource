package org.example.dto.service.space;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@RequiredArgsConstructor
@Getter
@Setter
public class SpaceTypeDto {
    private final Long id;
    private final String name;
    private final String displayName;
}

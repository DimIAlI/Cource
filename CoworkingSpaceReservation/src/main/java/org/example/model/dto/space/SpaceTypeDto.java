package org.example.model.dto.space;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class SpaceTypeDto {
    private final Long id;
    private final String name;
    private final String displayName;
}

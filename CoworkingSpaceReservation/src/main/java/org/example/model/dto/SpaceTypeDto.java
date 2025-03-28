package org.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class SpaceTypeDto {
    private final long id;
    private final String name;
    private final String displayName;
}

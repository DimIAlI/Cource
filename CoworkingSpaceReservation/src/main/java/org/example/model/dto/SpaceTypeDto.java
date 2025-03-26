package org.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SpaceTypeDto {
    private final long id;
    private final String name;
    private final String displayName;
}

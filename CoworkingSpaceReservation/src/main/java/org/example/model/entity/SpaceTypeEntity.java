package org.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SpaceTypeEntity {

    private final long id;
    private final String name;
    private final String displayName;

}

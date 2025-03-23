package org.example.model.entity;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceEntity {
    private long id;
    @EqualsAndHashCode.Include
    private SpaceTypeEntity type;
    @EqualsAndHashCode.Include
    private double price;
    private boolean available;
}


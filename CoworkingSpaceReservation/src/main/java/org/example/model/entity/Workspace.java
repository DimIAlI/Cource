package org.example.model.entity;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Workspace {
    private long id;
    @EqualsAndHashCode.Include
    private SpaceType type;
    @EqualsAndHashCode.Include
    private double price;
    private boolean available;
}


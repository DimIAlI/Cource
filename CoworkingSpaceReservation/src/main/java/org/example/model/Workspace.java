package org.example.model;

import lombok.*;


@Builder
@Data
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

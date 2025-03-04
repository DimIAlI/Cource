package org.example.model;

import lombok.*;

import java.util.List;

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
    //todo to remove - leads to a cyclic dependency
    @ToString.Exclude
    private List<Reservation> reservations;
}

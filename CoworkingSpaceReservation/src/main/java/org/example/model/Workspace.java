package org.example.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Workspace {
    private long id;
    @EqualsAndHashCode.Include
    private SpaceType type;
    @EqualsAndHashCode.Include
    private double price;
    private boolean available;
    @ToString.Exclude
    private List<Reservation> reservations;
}

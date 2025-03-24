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
    private long typeId;
    @EqualsAndHashCode.Include
    private double price;
    private boolean available;
}


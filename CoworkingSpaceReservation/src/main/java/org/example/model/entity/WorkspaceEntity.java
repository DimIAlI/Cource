package org.example.model.entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceEntity {
    private long id;
    private long typeId;
    private double price;
    private boolean available;
}


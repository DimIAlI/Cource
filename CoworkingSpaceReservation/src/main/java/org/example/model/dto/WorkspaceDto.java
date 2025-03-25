package org.example.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceDto {
    private long id;
    private SpaceTypeDto type;
    private double price;
    private boolean available;
}

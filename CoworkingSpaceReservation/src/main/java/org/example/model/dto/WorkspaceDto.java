package org.example.model.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkspaceDto {
    private long id;
    private SpaceTypeDto type;
    private double price;
    private boolean available;
}

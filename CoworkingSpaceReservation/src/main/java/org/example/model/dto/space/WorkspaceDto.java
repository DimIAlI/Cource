package org.example.model.dto.space;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkspaceDto {
    private Long id;
    private SpaceTypeDto type;
    private Double price;
    private Boolean available;
}

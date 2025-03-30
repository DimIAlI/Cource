package org.example.model.dto.space;

import lombok.*;

@Builder
@RequiredArgsConstructor
@Getter
@Setter
public class WorkspaceDto {
    private final Long id;
    private final SpaceTypeDto type;
    private final Double price;
    private final Boolean available;
}

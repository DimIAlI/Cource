package org.example.model.dto.space;

import lombok.*;

@Builder
@RequiredArgsConstructor
@Getter
@Setter
public class SpaceTypeDto {
    private final Long id;
    private final String name;
    private final String displayName;
}

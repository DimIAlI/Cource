package org.example.dto.service.space;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@RequiredArgsConstructor
@Getter
@Setter
public class WorkspaceDto {
    private final Long id;
    private final String type;
    private final Double price;
}

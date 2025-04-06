package org.example.service.mapper.entytyToDto;

import lombok.RequiredArgsConstructor;
import org.example.dto.service.space.WorkspaceDto;
import org.example.entity.space.WorkspaceEntity;
import org.example.service.SpaceTypeService;
import org.example.service.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkspaceMapper implements Mapper<WorkspaceDto, WorkspaceEntity> {

    private final SpaceTypeService spaceTypeService;

    @Override
    public WorkspaceDto mapTo(WorkspaceEntity elem) {
        Long typeId = elem.getType().getId();
        String typeDisplayName = spaceTypeService.getValue(typeId).getDisplayName();

        return WorkspaceDto.builder()
                .id(elem.getId())
                .type(typeDisplayName)
                .price(elem.getPrice())
                .build();
    }
}

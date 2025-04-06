package org.example.service.mapper.entytyToDto;

import org.example.dto.service.space.SpaceTypeDto;
import org.example.entity.space.SpaceTypeEntity;
import org.example.service.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class SpaceTypeMapper implements Mapper<SpaceTypeDto, SpaceTypeEntity> {
    @Override
    public SpaceTypeDto mapTo(SpaceTypeEntity elem) {
        return SpaceTypeDto.builder()
                .id(elem.getId())
                .name(elem.getName())
                .displayName(elem.getDisplayName())
                .build();
    }
}

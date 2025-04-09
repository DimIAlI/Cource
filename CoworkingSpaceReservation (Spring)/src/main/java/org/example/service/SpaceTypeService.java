package org.example.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.dto.service.space.SpaceTypeDto;
import org.example.entity.space.SpaceTypeEntity;
import org.example.repository.space.SpaceTypeRepository;
import org.example.service.mapper.entytyToDto.SpaceTypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SpaceTypeService {

    private final SpaceTypeRepository spaceTypeRepository;

    private final SpaceTypeMapper spaceTypeMapper;
    private Map<String, SpaceTypeDto> cachedByName;
    private Map<Long, SpaceTypeDto> cachedById;

    public Map<String, SpaceTypeDto> getValues() {
        return cachedByName;
    }

    public SpaceTypeDto getValue(String type) {
        return cachedByName.get(type);
    }

    public SpaceTypeDto getValue(long id) {
        return cachedById.get(id);
    }

    @PostConstruct
    @Transactional
    public void loadSpaceTypes() {

        List<SpaceTypeEntity> spaceTypes = spaceTypeRepository.findAll();

        Map<String, SpaceTypeDto> byName = new HashMap<>();
        Map<Long, SpaceTypeDto> byId = new HashMap<>();

        for (SpaceTypeEntity spaceType : spaceTypes) {

            SpaceTypeDto dto = spaceTypeMapper.mapTo(spaceType);

            byName.put(dto.getDisplayName(), dto);
            byId.put(dto.getId(), dto);
        }

        cachedByName = Collections.unmodifiableMap(byName);
        cachedById = Collections.unmodifiableMap(byId);
    }
}

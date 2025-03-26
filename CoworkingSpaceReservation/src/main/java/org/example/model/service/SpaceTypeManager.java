package org.example.model.service;

import org.example.model.dao.SpaceTypeDao;
import org.example.model.dto.SpaceTypeDto;
import org.example.model.entity.SpaceTypeEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpaceTypeManager {
    private static final SpaceTypeManager INSTANCE = new SpaceTypeManager();
    private static Map<String, SpaceTypeDto> cachedByName;
    private static Map<Long, SpaceTypeDto> cachedById;

    static {
        loadSpaceTypes();
    }

    private SpaceTypeManager() {
    }

    private static void loadSpaceTypes() {

        List<SpaceTypeEntity> spaceTypes = SpaceTypeDao.getInstance().findAll();

        Map<String, SpaceTypeDto> byName = new HashMap<>();
        Map<Long, SpaceTypeDto> byId = new HashMap<>();

        for (SpaceTypeEntity spaceType : spaceTypes) {

            SpaceTypeDto dto = SpaceTypeDto.builder()
                    .id(spaceType.getId())
                    .name(spaceType.getName())
                    .displayName(spaceType.getDisplayName())
                    .build();

            byName.put(dto.getDisplayName(), dto);
            byId.put(dto.getId(), dto);
        }

        cachedByName = Collections.unmodifiableMap(byName);
        cachedById = Collections.unmodifiableMap(byId);
    }

    public static SpaceTypeManager getInstance() {
        return INSTANCE;
    }

    public Map<String, SpaceTypeDto> getValues() {
        return cachedByName;
    }

    public SpaceTypeDto getValue(long id) {
        return cachedById.get(id);
    }
}

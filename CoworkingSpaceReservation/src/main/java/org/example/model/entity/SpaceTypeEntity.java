package org.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.model.dao.SpaceTypeDao;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class SpaceTypeEntity {

    private static Map<String, SpaceTypeEntity> cachedSpaceTypes;
    private final long id;
    private final String name;
    private final String displayName;

    //todo статики перенести в менеджер
    public static Map<String, SpaceTypeEntity> getValues() {
        if (cachedSpaceTypes == null) {
            loadSpaceTypes();
        }
        return cachedSpaceTypes;
    }

    private static void loadSpaceTypes() {
        SpaceTypeDao instance = SpaceTypeDao.getInstance();
        List<SpaceTypeEntity> spaceTypes = instance.findAll();

        cachedSpaceTypes = spaceTypes.stream()
                        .collect(Collectors.toUnmodifiableMap(SpaceTypeEntity::getName, spaceType -> spaceType));
    }
}

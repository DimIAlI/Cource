package org.example.model.service;

import org.example.model.dto.space.SpaceTypeDto;
import org.example.model.entity.space.SpaceTypeEntity;
import org.example.model.repository.space.SpaceTypeRepository;
import org.example.model.util.SessionManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpaceTypeManager {
    private static final SpaceTypeManager INSTANCE = new SpaceTypeManager();
    private static final SessionFactory sessionFactory = SessionManager.getFactory();
    private static Map<String, SpaceTypeDto> cachedByName;
    private static Map<Long, SpaceTypeDto> cachedById;

    static {
        loadSpaceTypes();
    }

    private SpaceTypeManager() {
    }
    public static SpaceTypeManager getInstance() {
        return INSTANCE;
    }
    private static void loadSpaceTypes() {

        List<SpaceTypeEntity> spaceTypes = getAllSpaces();

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

    private static List<SpaceTypeEntity> getAllSpaces() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        SpaceTypeRepository spaceTypeRepository = new SpaceTypeRepository(session);

        List<SpaceTypeEntity> spaceTypes = spaceTypeRepository.findAll();

        transaction.commit();
        return spaceTypes;
    }

    public Map<String, SpaceTypeDto> getValues() {
        return cachedByName;
    }

    public SpaceTypeDto getValue(long id) {
        return cachedById.get(id);
    }
}

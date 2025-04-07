package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.service.space.SpaceTypeDto;
import org.example.entity.space.SpaceTypeEntity;
import org.example.repository.space.SpaceTypeRepository;
import org.example.service.mapper.entytyToDto.SpaceTypeMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SpaceTypeService {

    private final SessionFactory sessionFactory;
    private final SpaceTypeMapper spaceTypeMapper;
    private Map<String, SpaceTypeDto> cachedByName;
    private Map<Long, SpaceTypeDto> cachedById;

    public Map<String, SpaceTypeDto> getValues() {
        if (cachedByName == null) loadSpaceTypes();
        return cachedByName;
    }

    public SpaceTypeDto getValue(String type) {
        if (cachedById == null) loadSpaceTypes();
        return cachedByName.get(type);
    }

    public SpaceTypeDto getValue(long id) {
        if (cachedById == null) loadSpaceTypes();
        return cachedById.get(id);
    }


    private void loadSpaceTypes() {

        List<SpaceTypeEntity> spaceTypes = getAllSpaces();

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

    private List<SpaceTypeEntity> getAllSpaces() {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        SpaceTypeRepository spaceTypeRepository = new SpaceTypeRepository(session);

        List<SpaceTypeEntity> spaceTypes = spaceTypeRepository.findAll();

        transaction.commit();
        return spaceTypes;
    }
}

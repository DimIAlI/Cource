package org.example.model.service;

import org.example.exceptions.IdNotFoundException;
import org.example.exceptions.NoAvailableSpacesException;
import org.example.exceptions.PlaceAlreadyExistException;
import org.example.model.dto.space.SpaceTypeDto;
import org.example.model.dto.space.WorkspaceDto;
import org.example.model.dto.filters.space.WorkspaceFilter;
import org.example.model.entity.space.SpaceTypeEntity;
import org.example.model.entity.space.WorkspaceEntity;
import org.example.model.repository.space.WorkspaceRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.*;

@Service
public class WorkspaceManager {
    private final SessionFactory sessionFactory;
    private final SpaceTypeManager spaceTypeManager;

    public WorkspaceManager(SessionFactory sessionFactory, SpaceTypeManager spaceTypeManager) {
        this.sessionFactory = sessionFactory;
        this.spaceTypeManager = spaceTypeManager;
    }

    public void add(SpaceTypeDto type, double price) {

        WorkspaceFilter filter = WorkspaceFilter.builder()
                .type(type)
                .price(price)
                .build();

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        WorkspaceRepository workspaceRepository = new WorkspaceRepository(session);

        List<WorkspaceEntity> notUniqueSpaces = workspaceRepository.findAll(filter);

        if (!notUniqueSpaces.isEmpty()) {
            transaction.rollback();
            throw new PlaceAlreadyExistException(type.getDisplayName(), price + "");
        }

        SpaceTypeEntity spaceTypeEntity = SpaceTypeEntity.builder()
                .id(type.getId())
                .build();

        WorkspaceEntity entity = WorkspaceEntity.builder()
                .type(spaceTypeEntity)
                .price(price)
                .available(true)
                .build();

        workspaceRepository.save(entity);
        transaction.commit();
    }

    public void remove(long id) {

        WorkspaceFilter filter = WorkspaceFilter.builder().id(id).build();

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        WorkspaceRepository workspaceRepository = new WorkspaceRepository(session);

        List<WorkspaceEntity> all = workspaceRepository.findAll(filter);

        if (all.isEmpty()) {
            transaction.rollback();
            throw new IdNotFoundException(id);
        }

        WorkspaceEntity entity = all.get(0);
        workspaceRepository.delete(entity);
        transaction.commit();
    }

    public List<WorkspaceDto> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        WorkspaceRepository workspaceRepository = new WorkspaceRepository(session);
        List<WorkspaceEntity> allWorkspaces = workspaceRepository.findAll();

        transaction.commit();
        return allWorkspaces.stream()
                .map(this::convertToDto)
                .collect(toList());
    }

    public List<WorkspaceDto> getAvailable(LocalDateTime startTime, LocalDateTime endTime) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        WorkspaceRepository workspaceRepository = new WorkspaceRepository(session);
        List<WorkspaceEntity> allAvailableSpaces = workspaceRepository.getAvailableBetween(startTime, endTime);
        if (allAvailableSpaces.isEmpty()) {
            transaction.rollback();
            throw new NoAvailableSpacesException();
        }
        transaction.commit();

        return allAvailableSpaces.stream()
                .map(this::convertToDto)
                .collect(toList());
    }

    public WorkspaceDto getWorkspace(long id) {

        WorkspaceFilter filter = WorkspaceFilter.builder().id(id).build();

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        WorkspaceRepository workspaceRepository = new WorkspaceRepository(session);
        List<WorkspaceEntity> allWorkspaces = workspaceRepository.findAll(filter);

        transaction.commit();

        return allWorkspaces.stream()
                .map(this::convertToDto)
                .findFirst()
                .orElseThrow(() -> new IdNotFoundException(id));
    }

    private WorkspaceDto convertToDto(WorkspaceEntity space) {

        return WorkspaceDto.builder()
                .id(space.getId())
                .type(spaceTypeManager.getValue(space.getType().getId()))
                .price(space.getPrice())
                .available(space.getAvailable())
                .build();
    }
}
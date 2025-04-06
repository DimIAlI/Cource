package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.service.space.SpaceTypeDto;
import org.example.dto.service.space.WorkspaceDto;
import org.example.dto.view.AddWorkspaceDto;
import org.example.dto.view.AvailableSpaceDto;
import org.example.dto.view.DeleteSpaceDto;
import org.example.entity.space.WorkspaceEntity;
import org.example.exception.workspace.IdNotFoundException;
import org.example.exception.workspace.NoAvailableSpacesException;
import org.example.exception.workspace.NoSpacesAddedException;
import org.example.exception.workspace.PlaceAlreadyExistException;
import org.example.repository.space.WorkspaceRepository;
import org.example.service.filters.space.WorkspaceFilter;
import org.example.service.mapper.EntityConverter;
import org.example.service.mapper.entytyToDto.WorkspaceMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final SessionFactory sessionFactory;
    private final SpaceTypeService spaceTypeManager;
    private final WorkspaceMapper workspaceMapper;
    private final EntityConverter entityConverter;

    public List<WorkspaceDto> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        WorkspaceRepository workspaceRepository = new WorkspaceRepository(session);
        List<WorkspaceEntity> allWorkspaces = workspaceRepository.findAll();

        if (allWorkspaces.isEmpty()) {
            transaction.rollback();
            throw new NoSpacesAddedException();
        }

        transaction.commit();
        return allWorkspaces.stream()
                .map(workspaceMapper::mapTo)
                .collect(toList());
    }

    public void add(AddWorkspaceDto workSpaceDto) {

        SpaceTypeDto spaceTypeDto = spaceTypeManager.getValue(workSpaceDto.getType());

        WorkspaceFilter filter = WorkspaceFilter.builder()
                .type(spaceTypeDto)
                .price(workSpaceDto.getPrice())
                .build();

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        WorkspaceRepository workspaceRepository = new WorkspaceRepository(session);

        List<WorkspaceEntity> notUniqueSpaces = workspaceRepository.findAll(filter);

        if (!notUniqueSpaces.isEmpty()) {
            transaction.rollback();
            throw new PlaceAlreadyExistException(spaceTypeDto.getDisplayName(), workSpaceDto.getPrice());
        }

        Long typeId = spaceTypeDto.getId();
        Double price = workSpaceDto.getPrice();
        WorkspaceEntity entity = entityConverter.convertSpaceToEntity(typeId, price);

        workspaceRepository.save(entity);
        transaction.commit();
    }

    public void remove(DeleteSpaceDto deleteSpaceDto) {

        Long id = deleteSpaceDto.getId();

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

    public List<WorkspaceDto> getAvailable(AvailableSpaceDto availableSpaceDto) {

        LocalDateTime startTime = availableSpaceDto.getStartTime();
        LocalDateTime endTime = availableSpaceDto.getEndTime();

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        WorkspaceRepository workspaceRepository = new WorkspaceRepository(session);
        List<WorkspaceEntity> allAvailableSpaces = workspaceRepository.getAvailableBetween(startTime, endTime);

        if (allAvailableSpaces.isEmpty()) {
            transaction.rollback();
            throw new NoAvailableSpacesException(startTime, endTime);
        }
        transaction.commit();

        return allAvailableSpaces.stream()
                .map(workspaceMapper::mapTo)
                .collect(toList());
    }
}

package org.example.model.service;

import org.example.exceptions.IdNotFoundException;
import org.example.exceptions.PlaceAlreadyExistException;
import org.example.model.dto.space.SpaceTypeDto;
import org.example.model.dto.space.WorkspaceDto;
import org.example.model.dto.filters.space.WorkspaceFilter;
import org.example.model.entity.space.SpaceTypeEntity;
import org.example.model.entity.space.WorkspaceEntity;
import org.example.model.repository.space.WorkspaceRepository;
import org.example.model.util.SessionManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.*;


public class WorkspaceManager {
    private static final WorkspaceManager INSTANCE = new WorkspaceManager();
    private final WorkspaceDao workspaceDaoInstance = WorkspaceDao.getInstance();

    private WorkspaceManager() {
    }

    public static WorkspaceManager getInstance() {
        return INSTANCE;
    }

    public void add(SpaceTypeDto type, double price) {

        WorkspaceEntity entity = WorkspaceEntity.builder()
                .typeId(type.getId())
                .price(price)
                .available(true)
                .build();
        try {
            workspaceDaoInstance.save(entity);
            //need to specify the exception so as not to catch the general
        } catch (SQLException exception) {
            throw new PlaceAlreadyExistException(type.getDisplayName(), price + "");
        }
    }

    public void remove(long id) {
        if (!workspaceDaoInstance.delete(id)) throw new IdNotFoundException(id);
    }

    public List<WorkspaceDto> getAll() {

        return workspaceDaoInstance.getAll().stream()
                .map(this::convertToDto)
                .collect(toList());
    }

    public List<WorkspaceDto> getAvailable(LocalDateTime startTime, LocalDateTime endTime) {

        return workspaceDaoInstance.getAvailableBetween(startTime, endTime).stream()
                .map(this::convertToDto)
                .collect(toList());
    }

    public WorkspaceDto getWorkspace(long id) {

        Filter filter = WorkspaceFilter.builder().id(id).build();

        return workspaceDaoInstance.getAllWithFilter(filter).stream()
                .map(this::convertToDto)
                .findFirst()
                .orElseThrow(() -> new IdNotFoundException(id));
    }

    private WorkspaceDto convertToDto(WorkspaceEntity space) {
        return WorkspaceDto.builder()
                .id(space.getId())
                .type(SpaceTypeManager.getInstance().getValue(space.getTypeId()))
                .price(space.getPrice())
                .available(space.isAvailable())
                .build();
    }
}
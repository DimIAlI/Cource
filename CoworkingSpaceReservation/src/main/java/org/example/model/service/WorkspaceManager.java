package org.example.model.service;

import org.example.exceptions.IdNotFoundException;
import org.example.exceptions.PlaceAlreadyExistException;
import org.example.model.dao.WorkspaceDao;
import org.example.model.dto.SpaceTypeDto;
import org.example.model.dto.WorkspaceDto;
import org.example.model.dto.filters.Filter;
import org.example.model.dto.filters.WorkspaceFilter;
import org.example.model.entity.WorkspaceEntity;

import java.sql.SQLException;
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
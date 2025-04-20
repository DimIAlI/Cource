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
import org.example.service.mapper.EntityConverter;
import org.example.service.mapper.entytyToDto.WorkspaceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final SpaceTypeService spaceTypeManager;
    private final WorkspaceMapper workspaceMapper;
    private final EntityConverter entityConverter;

    @Transactional(readOnly = true)
    public List<WorkspaceDto> getAll() {

        List<WorkspaceEntity> allWorkspaces = workspaceRepository.findAll();

        if (allWorkspaces.isEmpty()) {
            throw new NoSpacesAddedException();
        }

        return allWorkspaces.stream()
                .map(workspaceMapper::mapTo)
                .collect(toList());
    }

    @Transactional
    public void add(AddWorkspaceDto workSpaceDto) {

        SpaceTypeDto spaceType = spaceTypeManager.getValue(workSpaceDto.getType());
        Long spaceTypeId = spaceType.getId();
        Double price = workSpaceDto.getPrice();

        Optional<WorkspaceEntity> potentialNotUniqueWorkspace = workspaceRepository.findByTypeIdAndPrice(spaceTypeId, price);

        potentialNotUniqueWorkspace.ifPresentOrElse(space -> {
                    throw new PlaceAlreadyExistException(spaceType.getDisplayName(), price);
                },
                () -> {
                    WorkspaceEntity entity = entityConverter.convertSpaceToEntity(spaceTypeId, price);
                    workspaceRepository.save(entity);
                });
    }

    @Transactional
    public void remove(DeleteSpaceDto deleteSpaceDto) {

        Long id = deleteSpaceDto.getId();

        Optional<WorkspaceEntity> maybeWorkspace = workspaceRepository.findById(id);

        maybeWorkspace.ifPresentOrElse(workspaceRepository::delete, () -> {
            throw new IdNotFoundException(id);
        });
    }

    public List<WorkspaceDto> getAvailable(AvailableSpaceDto availableSpaceDto) {

        LocalDateTime startTime = availableSpaceDto.getStartTime();
        LocalDateTime endTime = availableSpaceDto.getEndTime();

        List<WorkspaceEntity> allAvailableSpaces = workspaceRepository.findAvailableBetween(startTime, endTime);

        if (allAvailableSpaces.isEmpty()) {
            throw new NoAvailableSpacesException(startTime, endTime);
        }

        return allAvailableSpaces.stream()
                .map(workspaceMapper::mapTo)
                .collect(toList());
    }
}

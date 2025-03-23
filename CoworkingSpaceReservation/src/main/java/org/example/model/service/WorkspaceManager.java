package org.example.model.service;

import lombok.Getter;
import org.example.exceptions.IdNotFoundException;
import org.example.exceptions.PlaceAlreadyExistException;
import org.example.model.entity.ReservationEntity;
import org.example.model.entity.SpaceTypeEntity;
import org.example.model.entity.WorkspaceEntity;
import org.example.model.storage.ApplicationState;
import org.example.model.storage.ApplicationStateManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class WorkspaceManager {
    private static final WorkspaceManager INSTANCE = new WorkspaceManager();
    private final Map<Long, WorkspaceEntity> WORKSPACES;
    @Getter
    private Long id;

    private WorkspaceManager() {
        ApplicationState appState = ApplicationStateManager.getInstance().getState();

        WORKSPACES = appState.getWorkspaces();
        id = appState.getLastWorkspaceId();
    }

    public static WorkspaceManager getInstance() {
        return INSTANCE;
    }

    public void add(SpaceTypeEntity type, double price) {

        if (!isWorkplaceExisted(type, price)) {

            WorkspaceEntity workspace = buildWorkspace(type, price);
            WORKSPACES.put(workspace.getId(), workspace);
        } else throw new PlaceAlreadyExistException(type.toString(), String.valueOf(price));
    }

    public void remove(long id) {
        if (WORKSPACES.containsKey(id)) {
            WORKSPACES.remove(id);
        } else throw new IdNotFoundException(id);
    }

    public List<WorkspaceEntity> getAvailable(LocalDateTime startTime, LocalDateTime endTime) {

        ReservationManager reservationManager = ReservationManager.getInstance();

        List<WorkspaceEntity> availablePlaces = new ArrayList<>(WORKSPACES.values());

        Map<Long, ReservationEntity> reservedPlaces = reservationManager.getAll();

        List<WorkspaceEntity> occupiedPlaces = reservedPlaces.values().stream()
                .filter(reservation -> hasConflictWithTimeRange(reservation, startTime, endTime))
                .map(ReservationEntity::getSpace)
                .distinct()
                .toList();

        availablePlaces.removeAll(occupiedPlaces);

        return availablePlaces;
    }

    public List<WorkspaceEntity> getAll() {
        return new ArrayList<>(WORKSPACES.values());
    }

    public WorkspaceEntity getWorkspace(long id) {
        if (WORKSPACES.containsKey(id)) {
            return WORKSPACES.get(id);
        } else throw new IdNotFoundException(id);
    }

    private WorkspaceEntity buildWorkspace(SpaceTypeEntity type, double price) {
        return WorkspaceEntity.builder()
                .id(generateId())
                .type(type)
                .price(price)
                .available(true)
                .build();
    }

    private boolean isWorkplaceExisted(SpaceTypeEntity type, double price) {
        return WORKSPACES.values().stream()
                .anyMatch(existingWorkspace ->
                        existingWorkspace.getType().equals(type) && existingWorkspace.getPrice() == price);
    }

    private long generateId() {
        return ++id;
    }

    private boolean hasConflictWithTimeRange(ReservationEntity reservation, LocalDateTime startTime, LocalDateTime endTime) {
        return reservation.getStartTime().isBefore(endTime) && reservation.getEndTime().isAfter(startTime);
    }
}
package org.example.model.service;

import lombok.Getter;
import org.example.exceptions.IdNotFoundException;
import org.example.exceptions.PlaceAlreadyExistException;
import org.example.model.entity.Reservation;
import org.example.model.entity.SpaceType;
import org.example.model.entity.Workspace;
import org.example.model.storage.ApplicationState;
import org.example.model.storage.ApplicationStateManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class WorkspaceManager {
    private static final WorkspaceManager INSTANCE = new WorkspaceManager();
    private final Map<Long, Workspace> WORKSPACES;
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

    public void add(SpaceType type, double price) {

        if (!isWorkplaceExisted(type, price)) {

            Workspace workspace = buildWorkspace(type, price);
            WORKSPACES.put(workspace.getId(), workspace);
        } else throw new PlaceAlreadyExistException(type.toString(), String.valueOf(price));
    }

    public void remove(long id) {
        if (WORKSPACES.containsKey(id)) {
            WORKSPACES.remove(id);
        } else throw new IdNotFoundException(id);
    }

    public List<Workspace> getAvailable(LocalDateTime startTime, LocalDateTime endTime) {

        ReservationManager reservationManager = ReservationManager.getInstance();

        List<Workspace> availablePlaces = new ArrayList<>(WORKSPACES.values());

        Map<Long, Reservation> reservedPlaces = reservationManager.getAll();

        List<Workspace> occupiedPlaces = reservedPlaces.values().stream()
                .filter(reservation -> hasConflictWithTimeRange(reservation, startTime, endTime))
                .map(Reservation::getSpace)
                .distinct()
                .toList();

        availablePlaces.removeAll(occupiedPlaces);

        return availablePlaces;
    }

    public List<Workspace> getAll() {
        return new ArrayList<>(WORKSPACES.values());
    }

    public Workspace getWorkspace(long id) {
        if (WORKSPACES.containsKey(id)) {
            return WORKSPACES.get(id);
        } else throw new IdNotFoundException(id);
    }

    private Workspace buildWorkspace(SpaceType type, double price) {
        return Workspace.builder()
                .id(generateId())
                .type(type)
                .price(price)
                .available(true)
                .build();
    }

    private boolean isWorkplaceExisted(SpaceType type, double price) {
        return WORKSPACES.values().stream()
                .anyMatch(existingWorkspace ->
                        existingWorkspace.getType().equals(type) && existingWorkspace.getPrice() == price);
    }

    private long generateId() {
        return ++id;
    }

    private boolean hasConflictWithTimeRange(Reservation reservation, LocalDateTime startTime, LocalDateTime endTime) {
        return reservation.getStartTime().isBefore(endTime) && reservation.getEndTime().isAfter(startTime);
    }
}
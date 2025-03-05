package org.example.model;

import org.example.exceptions.IdNotFoundException;
import org.example.exceptions.PlaceAlreadyExistException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorkspaceManager {
    private static final WorkspaceManager INSTANCE = new WorkspaceManager();
    private final Map<Long, Workspace> WORKSPACES;
    private long id;

    private WorkspaceManager() {
        WORKSPACES = new HashMap<>();
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

        List<Workspace> availablePlaces = WORKSPACES.values()
                .stream()
                .filter(Workspace::isAvailable)
                .collect(Collectors.toList());

        Map<Long, Reservation> reservedPlaces = reservationManager.getAll();

        List<Workspace> occupiedPlaces = reservedPlaces.values().stream()
                .filter(reservation -> !isAvailableAtTimeRange(reservation, startTime, endTime))
                .map(Reservation::getSpace)
                .distinct()
                .collect(Collectors.toList());

        availablePlaces.removeAll(occupiedPlaces);

        return availablePlaces;
    }

    public List<Workspace> getAll() {
        return new ArrayList<>(WORKSPACES.values());
    }

    private Workspace buildWorkspace(SpaceType type, double price) {
        return Workspace.builder()
                .id(generateId())
                .type(type)
                .price(price)
                .available(true)
                .reservations(new ArrayList<>())
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

    private boolean isAvailableAtTimeRange(Reservation reservation, LocalDateTime startTime, LocalDateTime endTime) {
        return reservation.getStartTime().isBefore(endTime) && reservation.getEndTime().isAfter(startTime);
    }

    public Workspace getWorkspace(long id) {
        if (WORKSPACES.containsKey(id)) {
            return WORKSPACES.get(id);
        } else throw new IdNotFoundException(id);
    }
}
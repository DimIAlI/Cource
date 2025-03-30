package org.example.controller;

import org.example.model.dto.space.SpaceTypeDto;
import org.example.model.dto.space.WorkspaceDto;
import org.example.model.service.WorkspaceManager;

import java.time.LocalDateTime;
import java.util.List;

public class WorkspaceController {
    private final WorkspaceManager workspaceManager;

    public WorkspaceController() {
        this.workspaceManager = WorkspaceManager.getInstance();
    }

    static WorkspaceController createWorkspaceController() {
        return new WorkspaceController();
    }

    public void addWorkspace(SpaceTypeDto type, double price) {
        workspaceManager.add(type, price);
    }

    public void removeWorkspace(long id) {
        workspaceManager.remove(id);
    }

    public List<WorkspaceDto> getAllSpaces() {
        return workspaceManager.getAll();
    }

    public List<WorkspaceDto> getAvailableSpaces(LocalDateTime startTime, LocalDateTime endTime) {
        return workspaceManager.getAvailable(startTime, endTime);
    }

    public WorkspaceDto getWorkspace(long id) {
        return workspaceManager.getWorkspace(id);
    }
}

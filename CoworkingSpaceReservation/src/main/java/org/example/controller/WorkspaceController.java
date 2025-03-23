package org.example.controller;

import org.example.model.entity.SpaceTypeEntity;
import org.example.model.entity.WorkspaceEntity;
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

    public void addWorkspace(SpaceTypeEntity type, double price) {
        workspaceManager.add(type, price);
    }

    public void removeWorkspace(long id) {
        workspaceManager.remove(id);
    }

    public List<WorkspaceEntity> getAllSpaces() {
        return workspaceManager.getAll();
    }

    public List<WorkspaceEntity> getAvailableSpaces(LocalDateTime startTime, LocalDateTime endTime) {
        return workspaceManager.getAvailable(startTime, endTime);
    }

    public WorkspaceEntity getWorkspace(long id) {
        return workspaceManager.getWorkspace(id);
    }
}

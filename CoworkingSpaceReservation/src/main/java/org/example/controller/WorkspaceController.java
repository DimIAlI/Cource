package org.example.controller;

import org.example.model.SpaceType;
import org.example.model.Workspace;
import org.example.model.WorkspaceManager;
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

    public void addWorkspace(SpaceType type, double price) {
        workspaceManager.add(type, price);
    }

    public void removeWorkspace(long id) {
        workspaceManager.remove(id);
    }

    public List<Workspace> getAllSpaces() {
        return workspaceManager.getAll();
    }

    public List<Workspace> getAvailableSpaces(LocalDateTime startTime, LocalDateTime endTime) {
        return workspaceManager.getAvailable(startTime, endTime);
    }

    public Workspace getWorkspace(long id) {
        return workspaceManager.getWorkspace(id);
    }
}

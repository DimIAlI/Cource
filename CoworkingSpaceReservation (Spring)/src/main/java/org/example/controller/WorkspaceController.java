package org.example.controller;

import org.example.model.dto.space.SpaceTypeDto;
import org.example.model.dto.space.WorkspaceDto;
import org.example.model.service.WorkspaceManager;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
@Controller
public class WorkspaceController {
    private final WorkspaceManager workspaceManager;

    public WorkspaceController(WorkspaceManager workspaceManager) {
        this.workspaceManager = workspaceManager;
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

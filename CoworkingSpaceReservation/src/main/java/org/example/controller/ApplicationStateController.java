package org.example.controller;

import org.example.model.storage.ApplicationStateManager;

public class ApplicationStateController {
    public void saveChanges() {
        ApplicationStateManager.getInstance().saveState();
    }
}

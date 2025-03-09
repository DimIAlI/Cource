package org.example.controller;

import org.example.model.storage.ApplicationStateManager;

class ApplicationStateController {
    void saveChanges() {
        ApplicationStateManager.getInstance().saveState();
    }
}

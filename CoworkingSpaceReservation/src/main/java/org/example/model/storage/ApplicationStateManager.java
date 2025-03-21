package org.example.model.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import org.example.model.service.ReservationManager;
import org.example.model.service.WorkspaceManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Getter
public class ApplicationStateManager {
    private static final String FILE_PATH = "lib/app_state.json";
    //must be initialized before INSTANCE
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private static final ApplicationStateManager INSTANCE = new ApplicationStateManager();
    private final ApplicationState state;

    public static ApplicationStateManager getInstance() {
        return INSTANCE;
    }

    private ApplicationStateManager() {
        state = loadState();
    }

    private ApplicationState loadState() {
        Path path = Paths.get(FILE_PATH);
        if (!Files.exists(path)) {
            throw new IllegalStateException("State file %s not found".formatted(FILE_PATH));
        }
        try {
            return objectMapper.readValue(path.toFile(), ApplicationState.class);
        } catch (IOException e) {
            throw new RuntimeException("Error loading data from the state file", e);
        }
    }

    public void saveState() {
        state.setLastWorkspaceId(WorkspaceManager.getInstance().getId());
        state.setLastReservationId(ReservationManager.getInstance().getId());
        try {
            Path path = Paths.get(FILE_PATH);
            objectMapper.writeValue(Files.newOutputStream(path, StandardOpenOption.TRUNCATE_EXISTING), state);
        } catch (IOException e) {
            throw new RuntimeException("Error saving data to the state file", e);
        }
    }
}

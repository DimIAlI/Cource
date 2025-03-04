package org.example.model.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Getter
public class ApplicationStateManager {
    private static final String FILE_PATH = "lib/app_state.json";
    //must be initialized before INSTANCE
    private static final ApplicationStateManager INSTANCE = new ApplicationStateManager();
    public static ApplicationStateManager getInstance() {
        return INSTANCE;
    }

    private final ObjectMapper objectMapper;
    private final ApplicationState state;
    private ApplicationStateManager() {
        objectMapper = new ObjectMapper();
        state = loadState();
    }

    private ApplicationState loadState() {
        Path path = Paths.get(FILE_PATH);
        if (!Files.exists(path)) {
            throw new IllegalStateException("Файл состояния не найден: " + FILE_PATH);
        }
        try {
            return objectMapper.readValue(path.toFile(), ApplicationState.class);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке данных из файла состояния", e);
        }
    }

    public void saveState() {
        try {
            Path path = Paths.get(FILE_PATH);
            objectMapper.writeValue(Files.newOutputStream(path, StandardOpenOption.TRUNCATE_EXISTING), state);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении данных в файл состояния", e);
        }
    }
}

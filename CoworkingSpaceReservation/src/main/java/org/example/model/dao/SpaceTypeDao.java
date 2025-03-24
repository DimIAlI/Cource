package org.example.model.dao;

import lombok.SneakyThrows;
import org.example.model.entity.SpaceTypeEntity;
import org.example.model.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SpaceTypeDao {
    private static final SpaceTypeDao INSTANCE = new SpaceTypeDao();

    private static final String FIND_ALL_SQL = """
            SELECT id, name, display_name FROM space_types;
            """;

    private SpaceTypeDao() {
    }

    public static SpaceTypeDao getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    public List<SpaceTypeEntity> findAll() {

        List<SpaceTypeEntity> entities = new ArrayList<>();

        try (Connection connection = ConnectionManager.get();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(FIND_ALL_SQL);

            while (resultSet.next()) {
                entities.add(buildEntity(resultSet));
            }
        }
        return entities;
    }

    private SpaceTypeEntity buildEntity(ResultSet resultSet) throws SQLException {
        return SpaceTypeEntity.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .displayName(resultSet.getString("display_name"))
                .build();
    }
}


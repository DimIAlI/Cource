package org.example.model.dao;

import lombok.SneakyThrows;
import org.example.model.entity.WorkspaceEntity;
import org.example.model.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WorkspaceDao extends AbstractDao<WorkspaceEntity> {

    private static final WorkspaceDao INSTANCE = new WorkspaceDao();
    private static final String SAVE_SQL = """
            INSERT INTO workspaces (type_id, price, available)
            VALUES (?,?,?)
            RETURNING id;
            """;
    private static final String DELETE_SQL = """
            DELETE FROM workspaces WHERE id = ?;
            """;
    private static final String FIND_ALL_SQL = """
            SELECT id, type_id, price, available
            FROM workspaces
            """;
    private static final String FIND_AVAILABLE_AT_PERIOD_SQL = FIND_ALL_SQL + """
             WHERE id NOT IN (
                    SELECT res.workspace_id FROM reservations as res
                    WHERE NOT (
                        res.end_time <= ?
                        OR res.start_time >= ?));
            """;
    private static final String UPDATE_SQL = """
            UPDATE workspaces
            SET available = ?
            WHERE id = ?
            """;

    private WorkspaceDao() {
    }

    public static WorkspaceDao getInstance() {
        return INSTANCE;
    }

    private static WorkspaceEntity buildWorkspace(ResultSet resultSet) throws SQLException {
        return WorkspaceEntity.builder()
                .id(resultSet.getLong("id"))
                .typeId(resultSet.getLong("type_id"))
                .price(resultSet.getDouble("price"))
                .available(resultSet.getBoolean("available"))
                .build();
    }

    @SneakyThrows
    public List<WorkspaceEntity> getAvailableBetween(LocalDateTime startTime, LocalDateTime endTime) {

        List<WorkspaceEntity> workspaces = new ArrayList<>();

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_AVAILABLE_AT_PERIOD_SQL)) {

            preparedStatement.setTimestamp(1, Timestamp.valueOf(startTime));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(endTime));

            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                workspaces.add(buildWorkspace(resultSet));
            }
        }
        return workspaces;
    }

    @SneakyThrows
    public void update(WorkspaceEntity workspace) {

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setBoolean(1, workspace.isAvailable());
            preparedStatement.setLong(2, workspace.getId());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    protected String getSaveSql() {
        return SAVE_SQL;
    }

    @Override
    protected String getDeleteSql() {
        return DELETE_SQL;
    }

    @Override
    protected String getFindAllSql() {
        return FIND_ALL_SQL;
    }

    @Override
    protected WorkspaceEntity buildEntity(ResultSet resultSet) throws SQLException {
        return WorkspaceDao.buildWorkspace(resultSet);
    }

    @Override
    protected void setSaveStatementParams(PreparedStatement preparedStatement, WorkspaceEntity entity) throws SQLException {
        preparedStatement.setLong(1, entity.getTypeId());
        preparedStatement.setDouble(2, entity.getPrice());
        preparedStatement.setBoolean(3, entity.isAvailable());
    }

    @Override
    protected WorkspaceEntity setGeneratedKey(WorkspaceEntity entity, long id) {
        entity.setId(id);
        return entity;
    }
}

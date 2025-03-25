package org.example.model.dao;

import org.example.model.entity.CustomerEntity;
import org.example.model.entity.ReservationEntity;
import org.example.model.entity.WorkspaceEntity;

import java.sql.*;

public class ReservationDao extends AbstractDao<ReservationEntity> {
    private static final ReservationDao INSTANCE = new ReservationDao();

    private static final String SAVE_SQL = """
            INSERT INTO reservations (customer_id, workspace_id, start_time, end_time)
            VALUES (?,?,?,?)
            RETURNING id;
            """;
    private static final String DELETE_SQL = """
            DELETE FROM reservations WHERE reservations.id = ?;
            """;
    private static final String FIND_ALL_SQL = """
            SELECT
            res.id res_id,
            customer_id,
            start_time,
            end_time,
                        
            work.id work_id,
            type_id,
            price,
                        
            login
            FROM reservations as res
            JOIN workspaces as work ON res.workspace_id = work.id
            JOIN customers as cus ON cus.id = res.customer_id
            """;

    private ReservationDao() {
    }

    public static ReservationDao getInstance() {
        return INSTANCE;
    }

    private static ReservationEntity buildReservation(ResultSet resultSet) throws SQLException {

        WorkspaceEntity workspace = WorkspaceEntity.builder()
                .id(resultSet.getLong("work_id"))
                .typeId(resultSet.getLong("type_id"))
                .price(resultSet.getDouble("price"))
                .build();

        CustomerEntity customer = CustomerEntity.builder()
                .login(resultSet.getString("login"))
                .build();

        return ReservationEntity.builder()
                .id(resultSet.getLong("res_id"))
                .customer(customer)
                .space(workspace)
                .startTime(resultSet.getTimestamp("start_time").toLocalDateTime())
                .endTime(resultSet.getTimestamp("end_time").toLocalDateTime())
                .build();
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
    protected ReservationEntity buildEntity(ResultSet resultSet) throws SQLException {
        return ReservationDao.buildReservation(resultSet);
    }

    @Override
    protected void setSaveStatementParams(PreparedStatement preparedStatement, ReservationEntity entity) throws SQLException {
        preparedStatement.setLong(1, entity.getCustomer().getId());
        preparedStatement.setLong(2, entity.getSpace().getId());
        preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getStartTime()));
        preparedStatement.setTimestamp(4, Timestamp.valueOf(entity.getEndTime()));
    }

    @Override
    protected ReservationEntity setGeneratedKey(ReservationEntity entity, long id) {
        entity.setId(id);
        return entity;
    }
}

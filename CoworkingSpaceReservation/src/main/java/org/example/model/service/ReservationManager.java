package org.example.model.service;

import org.example.exceptions.ReservationAlreadyExistException;
import org.example.exceptions.ReservationNotFoundForUserException;
import org.example.exceptions.WorkspaceAlreadyBookedAtTimeException;
import org.example.model.dao.ReservationDao;
import org.example.model.dao.WorkspaceDao;
import org.example.model.dto.CustomerDto;
import org.example.model.dto.ReservationDto;
import org.example.model.dto.WorkspaceDto;
import org.example.model.dto.filters.Filter;
import org.example.model.dto.filters.ReservationFilter;
import org.example.model.entity.CustomerEntity;
import org.example.model.entity.ReservationEntity;
import org.example.model.entity.WorkspaceEntity;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ReservationManager {
    private static final ReservationManager INSTANCE = new ReservationManager();
    private final ReservationDao reservationDaoInstance = ReservationDao.getInstance();
    private final SpaceTypeManager spaceTypeManager = SpaceTypeManager.getInstance();

    private ReservationManager() {

    }

    public static ReservationManager getInstance() {
        return INSTANCE;
    }

    public void add(CustomerDto customer, WorkspaceDto space, LocalDateTime startTime, LocalDateTime endTime) {

        WorkspaceDao workspaceDao = WorkspaceDao.getInstance();

        ReservationEntity reservationEntity = convertToEntity(customer, space, startTime, endTime);
        WorkspaceEntity workspace = convertToEntity(space);

        checkWorkspaceAvailability(startTime, endTime, workspace);


        try {
            reservationDaoInstance.save(reservationEntity);
        } catch (SQLException e) {
            throw new ReservationAlreadyExistException();
        }

        workspaceDao.update(workspace);
    }

    public void remove(long id, CustomerDto currentUser) {

        CustomerEntity customerEntity = convertToEntity(currentUser);

        Filter filter = ReservationFilter.builder()
                .id(id)
                .customer(customerEntity)
                .build();

        List<ReservationEntity> allWithFilter = reservationDaoInstance.getAllWithFilter(filter);

        if (allWithFilter.isEmpty())
            throw new ReservationNotFoundForUserException(id);

        reservationDaoInstance.delete(id);
    }

    public List<ReservationDto> getAll() {

        return reservationDaoInstance.getAll().stream()
                .map(this::convertToReservationDto)
                .collect(Collectors.toList());
    }

    public List<ReservationDto> getCustomerReservations(CustomerDto customer) {

        CustomerEntity customerEntity = convertToEntity(customer);

        Filter filter = ReservationFilter.builder()
                .customer(customerEntity).
                build();

        return reservationDaoInstance.getAllWithFilter(filter).stream().
                map(this::convertToReservationDto)
                .collect(Collectors.toList());
    }

    private void checkWorkspaceAvailability(LocalDateTime startTime, LocalDateTime endTime, WorkspaceEntity workspace) {
        //variable to avoid effectively final constraint
        long id = workspace.getId();

        WorkspaceDao.getInstance().getAvailableBetween(startTime, endTime)
                .stream()
                .filter(w -> w.getId() == id)
                .findFirst()
                .orElseThrow(WorkspaceAlreadyBookedAtTimeException::new);
    }

    private ReservationDto convertToReservationDto(ReservationEntity entity) {
        //не нужен вообще для getCustomerRes
        CustomerDto customerDto = CustomerDto.builder()
                //не нужен для getAll
                .id(entity.getCustomer().getId())
                .login(entity.getCustomer().getLogin())
                .build();

        WorkspaceDto workspaceDto = WorkspaceDto.builder()
                .id(entity.getSpace().getId())
                .type(spaceTypeManager.getValue(entity.getSpace().getTypeId()))
                .price(entity.getSpace().getPrice())
                //не нужен для getAll/getCustomerRes
                .available(entity.getSpace().isAvailable())
                .build();

        return ReservationDto.builder()
                .id(entity.getId())
                //не нужен для getCustomerRes
                .customer(customerDto)
                .space(workspaceDto)
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .build();
    }

    private ReservationEntity convertToEntity(CustomerDto customerDto, WorkspaceDto workspaceDto, LocalDateTime startTime, LocalDateTime endTime) {
        return ReservationEntity.builder()
                .space(convertToEntity(workspaceDto))
                .customer(convertToEntity(customerDto))
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    private CustomerEntity convertToEntity(CustomerDto customer) {
        return CustomerEntity.builder()
                .id(customer.getId())
                .login(customer.getLogin())
                .build();
    }

    private WorkspaceEntity convertToEntity(WorkspaceDto space) {
        return WorkspaceEntity.builder()
                .id(space.getId())
                .typeId(space.getType().getId())
                .price(space.getPrice())
                .available(space.isAvailable())
                .build();
    }
}

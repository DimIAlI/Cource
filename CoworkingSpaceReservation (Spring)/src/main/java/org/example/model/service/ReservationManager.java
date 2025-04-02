package org.example.model.service;

import org.example.exceptions.ReservationAlreadyExistException;
import org.example.exceptions.ReservationNotFoundForUserException;
import org.example.exceptions.WorkspaceAlreadyBookedAtTimeException;
import org.example.model.dto.account.CustomerDto;
import org.example.model.dto.space.ReservationDto;
import org.example.model.dto.space.WorkspaceDto;
import org.example.model.dto.filters.space.ReservationFilter;
import org.example.model.entity.account.CustomerEntity;
import org.example.model.entity.space.ReservationEntity;
import org.example.model.entity.space.SpaceTypeEntity;
import org.example.model.entity.space.WorkspaceEntity;
import org.example.model.repository.space.ReservationRepository;
import org.example.model.repository.space.WorkspaceRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class ReservationManager {

    private final SpaceTypeManager spaceTypeManager;
    private final SessionFactory sessionFactory;

    public ReservationManager(SpaceTypeManager spaceTypeManager, SessionFactory sessionFactory) {
        this.spaceTypeManager = spaceTypeManager;
        this.sessionFactory = sessionFactory;
    }

    public void add(CustomerDto customer, WorkspaceDto space, LocalDateTime startTime, LocalDateTime endTime) {

        WorkspaceEntity workspace = convertToEntity(space);

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            checkWorkspaceAvailability(startTime, endTime, workspace);
        } catch (ReservationAlreadyExistException exception) {
            transaction.rollback();
            throw exception;
        }
        ReservationFilter reservationFilter = ReservationFilter.builder()
                .space(space)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        ReservationRepository reservationRepository = new ReservationRepository(session);

        List<ReservationEntity> notUniqueReservations = reservationRepository.findAll(reservationFilter);

        if (!notUniqueReservations.isEmpty()) {
            transaction.rollback();
            throw new ReservationAlreadyExistException();
        }

        ReservationEntity reservationEntity = convertToEntity(customer, space, startTime, endTime);
        reservationRepository.save(reservationEntity);

        WorkspaceRepository workspaceRepository = new WorkspaceRepository(session);
        workspaceRepository.update(workspace);

        transaction.commit();
    }

    public void remove(Long id, CustomerDto currentUser) {

        ReservationFilter filter = ReservationFilter.builder().
                id(id)
                .customer(currentUser)
                .build();

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        ReservationRepository reservationRepository = new ReservationRepository(session);

        List<ReservationEntity> existedReservations = reservationRepository.findAll(filter);

        if (existedReservations.isEmpty()) {
            transaction.rollback();
            throw new ReservationNotFoundForUserException(id);
        }

        ReservationEntity entityToRemove = existedReservations.get(0);

        reservationRepository.delete(entityToRemove);
        transaction.commit();
    }

    public List<ReservationDto> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        ReservationRepository reservationRepository = new ReservationRepository(session);

        List<ReservationEntity> reservations = reservationRepository.findAll();

        transaction.commit();

        return reservations.stream()
                .map(this::convertToReservationDto)
                .collect(Collectors.toList());
    }

    public List<ReservationDto> getCustomerReservations(CustomerDto customer) {

        ReservationFilter filter = ReservationFilter.builder()
                .customer(customer).
                build();

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        ReservationRepository reservationRepository = new ReservationRepository(session);

        List<ReservationEntity> reservations = reservationRepository.findAll(filter);

        transaction.commit();

        return reservations.stream().
                map(this::convertToReservationDto)
                .collect(Collectors.toList());
    }

    private void checkWorkspaceAvailability(LocalDateTime startTime, LocalDateTime endTime, WorkspaceEntity workspace) {

        WorkspaceRepository workspaceRepository = new WorkspaceRepository(sessionFactory.getCurrentSession());

        //variable to avoid effectively final constraint
        long id = workspace.getId();

        List<WorkspaceEntity> availableSpaces = workspaceRepository.getAvailableBetween(startTime, endTime);

        availableSpaces
                .stream()
                .filter(w -> w.getId() == id)
                .findFirst()
                .orElseThrow(WorkspaceAlreadyBookedAtTimeException::new);
    }

    private ReservationDto convertToReservationDto(ReservationEntity entity) {

        CustomerDto customerDto = CustomerDto.builder()
                .id(entity.getCustomer().getId())
                .login(entity.getCustomer().getLogin())
                .build();

        WorkspaceDto workspaceDto = WorkspaceDto.builder()
                .id(entity.getSpace().getId())
                .type(spaceTypeManager.getValue(entity.getSpace().getType().getId()))
                .price(entity.getSpace().getPrice())
                .available(entity.getSpace().getAvailable())
                .build();

        return ReservationDto.builder()
                .id(entity.getId())
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

        SpaceTypeEntity spaceTypeEntity = SpaceTypeEntity
                .builder()
                .id(space.getType().getId())
                .build();

        return WorkspaceEntity.builder()
                .id(space.getId())
                .type(spaceTypeEntity)
                .price(space.getPrice())
                .available(space.getAvailable())
                .build();
    }
}

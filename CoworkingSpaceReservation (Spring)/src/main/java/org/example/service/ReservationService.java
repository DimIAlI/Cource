package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.service.account.CustomerDto;
import org.example.dto.service.space.ReservationDto;
import org.example.dto.view.AddReservationDto;
import org.example.dto.view.DeleteReservationDto;
import org.example.entity.space.ReservationEntity;
import org.example.entity.space.WorkspaceEntity;
import org.example.exception.reservation.NoReservationExistException;
import org.example.exception.reservation.ReservationNotFoundForUserException;
import org.example.exception.reservation.UserHasNoReservationsException;
import org.example.exception.reservation.WorkspaceAlreadyBookedAtTimeException;
import org.example.repository.space.ReservationRepository;
import org.example.repository.space.WorkspaceRepository;
import org.example.service.filters.space.ReservationFilter;
import org.example.service.mapper.EntityConverter;
import org.example.service.mapper.entytyToDto.ReservationMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final SessionFactory sessionFactory;
    private final ReservationMapper reservationMapper;
    private final EntityConverter entityConverter;


    public void add(AddReservationDto reservationDto) {

        Long spaceId = reservationDto.getSpaceId();
        Long customerId = reservationDto.getCustomerId();
        LocalDateTime startTime = reservationDto.getStartTime();
        LocalDateTime endTime = reservationDto.getEndTime();


        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            checkWorkspaceAvailability(spaceId, startTime, endTime);
        } catch (WorkspaceAlreadyBookedAtTimeException exception) {
            transaction.rollback();
            throw exception;
        }

        ReservationRepository reservationRepository = new ReservationRepository(session);

        ReservationEntity reservationEntity = entityConverter.convertToReservationEntity(customerId, spaceId, startTime, endTime);
        reservationRepository.save(reservationEntity);

        transaction.commit();
    }

    public void remove(DeleteReservationDto deleteReservationDto) {

        Long userId = deleteReservationDto.getCustomerId();
        Long reservationId = deleteReservationDto.getReservationId();

        ReservationFilter filter = ReservationFilter.builder().
                id(reservationId)
                .customerId(userId)
                .build();

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        ReservationRepository reservationRepository = new ReservationRepository(session);

        List<ReservationEntity> existedReservations = reservationRepository.findAll(filter);

        if (existedReservations.isEmpty()) {
            transaction.rollback();
            throw new ReservationNotFoundForUserException(reservationId);
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

        if (reservations.isEmpty()) {
            transaction.rollback();
            throw new NoReservationExistException();
        }

        transaction.commit();

        return reservations.stream()
                .map(reservationMapper::mapTo)
                .collect(Collectors.toList());
    }

    public List<ReservationDto> getCustomerReservations(CustomerDto customer) {

        ReservationFilter filter = ReservationFilter.builder()
                .customerId(customer.getId()).
                build();

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        ReservationRepository reservationRepository = new ReservationRepository(session);

        List<ReservationEntity> reservations = reservationRepository.findAll(filter);

        if (reservations.isEmpty()) {
            transaction.rollback();
            throw new UserHasNoReservationsException();
        }

        transaction.commit();

        return reservations.stream().
                map(reservationMapper::mapTo)
                .collect(Collectors.toList());
    }

    private void checkWorkspaceAvailability(Long id, LocalDateTime startTime, LocalDateTime endTime) {

        WorkspaceRepository workspaceRepository = new WorkspaceRepository(sessionFactory.getCurrentSession());

        //variable to avoid effectively final constraint
        long staticId = id;
        List<WorkspaceEntity> availableSpaces = workspaceRepository.getAvailableBetween(startTime, endTime);

        availableSpaces
                .stream()
                .filter(w -> w.getId() == staticId)
                .findFirst()
                .orElseThrow(() -> new WorkspaceAlreadyBookedAtTimeException(id, startTime, endTime));
    }
}

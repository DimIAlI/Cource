package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.service.space.ReservationDto;
import org.example.dto.view.AddReservationDto;
import org.example.dto.view.CustomUserDetails;
import org.example.dto.view.DeleteReservationDto;
import org.example.entity.space.ReservationEntity;
import org.example.entity.space.WorkspaceEntity;
import org.example.exception.reservation.NoReservationExistException;
import org.example.exception.reservation.ReservationNotFoundForUserException;
import org.example.exception.reservation.UserHasNoReservationsException;
import org.example.exception.reservation.WorkspaceAlreadyBookedAtTimeException;
import org.example.repository.space.ReservationRepository;
import org.example.repository.space.WorkspaceRepository;
import org.example.service.mapper.EntityConverter;
import org.example.service.mapper.entytyToDto.ReservationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final WorkspaceRepository workspaceRepository;
    private final ReservationMapper reservationMapper;
    private final EntityConverter entityConverter;

    @Transactional
    public void add(AddReservationDto reservationDto) {

        Long spaceId = reservationDto.getSpaceId();
        Long customerId = reservationDto.getCustomerId();
        LocalDateTime startTime = reservationDto.getStartTime();
        LocalDateTime endTime = reservationDto.getEndTime();

        checkWorkspaceAvailability(spaceId, startTime, endTime);

        ReservationEntity reservationEntity = entityConverter.convertToReservationEntity(customerId, spaceId, startTime, endTime);
        reservationRepository.save(reservationEntity);
    }

    @Transactional
    public void remove(DeleteReservationDto deleteReservationDto) {

        Long reservationId = deleteReservationDto.getReservationId();
        Long userId = deleteReservationDto.getCustomerId();

        Optional<ReservationEntity> maybeReservation = reservationRepository.findByIdAndCustomerId(reservationId, userId);

        maybeReservation.ifPresentOrElse(reservationRepository::delete,
                () -> {
                    throw new ReservationNotFoundForUserException(reservationId);
                });
    }

    public List<ReservationDto> getAll() {

        List<ReservationEntity> reservations = reservationRepository.findAll();

        if (reservations.isEmpty()) {
            throw new NoReservationExistException();
        }

        return reservations.stream()
                .map(reservationMapper::mapTo)
                .collect(Collectors.toList());
    }

    public List<ReservationDto> getCustomerReservations(CustomUserDetails customer) {

        Long customerId = customer.getId();

        List<ReservationEntity> reservations = reservationRepository.findByCustomerId(customerId);

        if (reservations.isEmpty()) {
            throw new UserHasNoReservationsException();
        }

        return reservations.stream().
                map(reservationMapper::mapTo)
                .collect(Collectors.toList());
    }

    private void checkWorkspaceAvailability(Long id, LocalDateTime startTime, LocalDateTime endTime) {

        List<WorkspaceEntity> availableSpaces = workspaceRepository.findAvailableBetween(startTime, endTime);

        availableSpaces
                .stream()
                .filter(w -> w.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new WorkspaceAlreadyBookedAtTimeException(id, startTime, endTime));
    }
}

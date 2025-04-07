package org.example.service.mapper;

import org.example.entity.account.AdminEntity;
import org.example.entity.account.CustomerEntity;
import org.example.entity.space.ReservationEntity;
import org.example.entity.space.SpaceTypeEntity;
import org.example.entity.space.WorkspaceEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EntityConverter {

    public SpaceTypeEntity convertToSpaceTypeEntity(Long id) {
        return SpaceTypeEntity.builder()
                .id(id)
                .build();
    }

    public AdminEntity convertToAdminEntity(String login) {
        return AdminEntity.builder()
                .login(login)
                .build();
    }

    public WorkspaceEntity convertSpaceToEntity(Long id, Double price) {
        SpaceTypeEntity spaceTypeEntity = convertToSpaceTypeEntity(id);

        return WorkspaceEntity.builder()
                .type(spaceTypeEntity)
                .price(price)
                .build();
    }

    public CustomerEntity convertToCustomerEntity(String login) {
        return CustomerEntity.builder()
                .login(login)
                .build();
    }

    public ReservationEntity convertToReservationEntity(Long customerId, Long spaceId, LocalDateTime startTime, LocalDateTime endTime) {
        return ReservationEntity.builder()
                .space(convertSpaceToEntity(spaceId))
                .customer(convertCustomerToEntity(customerId))
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    private CustomerEntity convertCustomerToEntity(Long customerId) {
        return CustomerEntity.builder()
                .id(customerId)
                .build();
    }

    private WorkspaceEntity convertSpaceToEntity(Long spaceId) {

        return WorkspaceEntity.builder()
                .id(spaceId)
                .build();
    }
}

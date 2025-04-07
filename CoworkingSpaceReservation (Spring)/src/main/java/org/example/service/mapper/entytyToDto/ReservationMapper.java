package org.example.service.mapper.entytyToDto;

import lombok.RequiredArgsConstructor;
import org.example.dto.service.space.ReservationDto;
import org.example.dto.service.space.WorkspaceDto;
import org.example.entity.space.ReservationEntity;
import org.example.service.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationMapper implements Mapper<ReservationDto, ReservationEntity> {

    private final WorkspaceMapper workspaceMapper;

    @Override
    public ReservationDto mapTo(ReservationEntity elem) {
        String login = elem.getCustomer().getLogin();
        WorkspaceDto workspaceDto = workspaceMapper.mapTo(elem.getSpace());

        return ReservationDto.builder()
                .id(elem.getId())
                .customerLogin(login)
                .space(workspaceDto)
                .startTime(elem.getStartTime())
                .endTime(elem.getEndTime())
                .build();
    }
}


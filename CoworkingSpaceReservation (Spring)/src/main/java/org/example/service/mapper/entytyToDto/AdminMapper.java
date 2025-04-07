package org.example.service.mapper.entytyToDto;

import org.example.dto.service.account.AdminDto;
import org.example.entity.account.AdminEntity;
import org.example.service.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper implements Mapper<AdminDto, AdminEntity> {
    @Override
    public AdminDto mapTo(AdminEntity elem) {

        return AdminDto.builder()
                .id(elem.getId())
                .login(elem.getLogin())
                .build();
    }
}

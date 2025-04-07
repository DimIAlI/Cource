package org.example.service.mapper.entytyToDto;

import org.example.dto.service.account.CustomerDto;
import org.example.entity.account.CustomerEntity;
import org.example.service.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper implements Mapper<CustomerDto, CustomerEntity> {
    @Override
    public CustomerDto mapTo(CustomerEntity elem) {

        return CustomerDto.builder()
                .id(elem.getId())
                .login(elem.getLogin())
                .build();
    }
}

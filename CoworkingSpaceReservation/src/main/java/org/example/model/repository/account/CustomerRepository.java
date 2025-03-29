package org.example.model.repository.account;

import org.example.model.dto.filters.account.CustomerFilter;
import org.example.model.entity.account.CustomerEntity;

import javax.persistence.EntityManager;

public class CustomerRepository extends BaseUserRepository<Long, CustomerEntity, CustomerFilter> {

    public CustomerRepository(EntityManager manager) {
        super(manager, CustomerEntity.class);
    }

}

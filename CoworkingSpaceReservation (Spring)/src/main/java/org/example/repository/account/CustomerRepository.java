package org.example.repository.account;

import jakarta.persistence.EntityManager;
import org.example.entity.account.CustomerEntity;
import org.example.service.filters.account.CustomerFilter;


public class CustomerRepository extends BaseUserRepository<Long, CustomerEntity, CustomerFilter> {

    public CustomerRepository(EntityManager manager) {
        super(manager, CustomerEntity.class);
    }

}

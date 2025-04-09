package org.example.repository.account;

import org.example.entity.account.CustomerEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends UserRepository<CustomerEntity> {
}

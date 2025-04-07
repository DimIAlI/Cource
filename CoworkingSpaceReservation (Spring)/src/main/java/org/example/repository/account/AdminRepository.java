package org.example.repository.account;

import jakarta.persistence.EntityManager;
import org.example.entity.account.AdminEntity;
import org.example.service.filters.account.AdminFilter;

public class AdminRepository extends BaseUserRepository<Long, AdminEntity, AdminFilter> {

    public AdminRepository(EntityManager manager) {
        super(manager, AdminEntity.class);
    }
}

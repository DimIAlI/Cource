package org.example.model.repository.account;

import org.example.model.dto.filters.account.AdminFilter;
import org.example.model.entity.account.AdminEntity;

import javax.persistence.EntityManager;

public class AdminRepository extends BaseUserRepository<Long, AdminEntity, AdminFilter> {

    public AdminRepository(EntityManager manager) {
        super(manager, AdminEntity.class);
    }

}

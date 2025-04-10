package org.example.repository.account;

import org.example.entity.account.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository <T extends UserEntity> extends JpaRepository<T, Long> {

    Optional<T> findByLogin(String login);
}

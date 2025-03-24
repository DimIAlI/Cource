package org.example.model.dao;

import org.example.model.entity.UserEntity;

import java.util.Optional;

public interface UserDao<T extends UserEntity> {

    T save(T user);

    Optional<T> findByLogin(String login);

}

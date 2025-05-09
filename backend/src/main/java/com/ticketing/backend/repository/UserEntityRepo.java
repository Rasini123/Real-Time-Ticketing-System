package com.ticketing.backend.repository;

import com.ticketing.backend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepo extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
}

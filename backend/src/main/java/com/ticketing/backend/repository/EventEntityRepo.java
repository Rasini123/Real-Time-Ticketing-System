package com.ticketing.backend.repository;

import com.ticketing.backend.entities.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventEntityRepo extends JpaRepository<EventEntity, Integer> {
    List<EventEntity> findByIsActiveTrueAndUserId(int userId);

    List<EventEntity> findByIsActiveFalseAndUserId(int userId);

    List<EventEntity> findByIsActiveTrue();
}

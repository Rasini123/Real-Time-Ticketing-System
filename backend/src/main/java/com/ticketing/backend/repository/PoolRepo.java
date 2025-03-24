package com.ticketing.backend.repository;

import com.ticketing.backend.entities.TicketPoolData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface PoolRepo extends JpaRepository<TicketPoolData, Integer> {

}

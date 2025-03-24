package com.ticketing.backend.repository;

import com.ticketing.backend.entities.TicketHistoryTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketHistoryRepo extends JpaRepository<TicketHistoryTable, Integer> {

    List<TicketHistoryTable> findAllByUserId(int userId);
}

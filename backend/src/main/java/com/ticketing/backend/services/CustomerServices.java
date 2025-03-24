package com.ticketing.backend.services;

import com.ticketing.backend.entities.EventEntity;
import com.ticketing.backend.entities.TicketHistoryTable;
import com.ticketing.backend.repository.EventEntityRepo;
import com.ticketing.backend.repository.TicketHistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServices {
    @Autowired
    private EventEntityRepo eventEntityRepo;

    @Autowired
    private TicketHistoryRepo ticketHistoryRepo;

    public List<EventEntity> getAlEvents(){
        return eventEntityRepo.findByIsActiveTrue();
    }

    public List<TicketHistoryTable> getTicketHistory(int userid){
        return ticketHistoryRepo.findAllByUserId(userid);
    }
}

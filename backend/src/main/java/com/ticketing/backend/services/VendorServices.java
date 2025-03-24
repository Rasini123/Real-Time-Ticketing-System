package com.ticketing.backend.services;

import com.ticketing.backend.entities.EventEntity;
import com.ticketing.backend.repository.EventEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorServices {
    @Autowired
    private EventEntityRepo eventEntityRepo;

    public List<EventEntity> getAlEvents(int userId){
        return eventEntityRepo.findByIsActiveTrueAndUserId(userId);
    }

    public List<EventEntity> getAllInactiveEvents(int userId){
        return eventEntityRepo.findByIsActiveFalseAndUserId(userId);
    }
}

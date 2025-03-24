package com.ticketing.backend.services;

import com.ticketing.backend.dto.EventConfiguration;
import com.ticketing.backend.entities.EventEntity;
import com.ticketing.backend.entities.TicketPoolData;
import com.ticketing.backend.repository.EventEntityRepo;
import com.ticketing.backend.repository.PoolRepo;
import com.ticketing.backend.repository.TicketHistoryRepo;
import com.ticketing.backend.threads.Customer;
import com.ticketing.backend.threads.TicketPool;
import com.ticketing.backend.threads.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Optional;

@Service
public class TicketServices {
    @Autowired
    private EventEntityRepo eventEntityRepo;

    @Autowired
    private TicketHistoryRepo ticketHistoryRepo;

    @Autowired
    private PoolRepo poolRepo;

    private LinkedList<TicketPool> ticketPoolsList = new LinkedList<>();

    public boolean createEvent(EventConfiguration eventConfiguration){
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventName(eventConfiguration.getEventName());
        eventEntity.setEventLocation(eventConfiguration.getEventLocation());
        eventEntity.setDate(eventConfiguration.getDate());
        eventEntity.setCustomerRetrieveRate(eventConfiguration.getCustomerRetrieveRate());
        eventEntity.setMaxTicketCapacity(eventConfiguration.getMaxTicketCapacity());
        eventEntity.setTicketPrice(eventConfiguration.getTicketPrice());
        eventEntity.setTotalTickets(eventConfiguration.getTotalTickets());
        eventEntity.setTicketReleaseRate(eventConfiguration.getTicketReleaseRate());
        eventEntity.setActive(true);
        eventEntity.setUserId(eventConfiguration.getUserId());
        eventEntityRepo.save(eventEntity);

        TicketPool ticketPool = new TicketPool(eventConfiguration.getMaxTicketCapacity(), eventEntity.getEventId(), poolRepo);
        ticketPoolsList.add(ticketPool);

        TicketPoolData ticketPoolData = new TicketPoolData();
        ticketPoolData.setCount(0);
        ticketPoolData.setRemaining(eventEntity.getTotalTickets());
        ticketPoolData.setSessionId(eventEntity.getEventId());
        poolRepo.save(ticketPoolData);

        Vendor vendor = new Vendor(ticketPool, eventEntity.getTotalTickets(), eventEntity.getTicketReleaseRate());
        Thread thread = new Thread(vendor);
        thread.start();

        return true;
    }

    public TicketPoolData getTicketPool(int eventId){
        Optional<TicketPoolData> poolRepoById = poolRepo.findById(eventId);
        if (poolRepoById.isEmpty()){
            return null;
        }
        return poolRepoById.get();
    }

    public void getTickets(int eventId, int quantity, int userId){
        Optional<EventEntity> eventEntityRepoById = eventEntityRepo.findById(eventId);
        if (eventEntityRepoById.isPresent()) {
            TicketPool ticketPool = getPool(eventId);
            Customer customer = new Customer(ticketPool, eventEntityRepoById.get(), ticketHistoryRepo, quantity, userId);
            Thread thread = new Thread(customer);
            thread.start();
        }
    }

    private TicketPool getPool(int eventId){
        for (int i = 0; i < ticketPoolsList.size(); i++) {
            if (ticketPoolsList.get(i).getEventId() == eventId){
                return ticketPoolsList.get(i);
            }
        }
        return null;
    }

    public String stopEvent(int eventId){
        Optional<EventEntity> eventEntityRepoById = eventEntityRepo.findById(eventId);
        if (eventEntityRepoById.isPresent()) {
            eventEntityRepoById.get().setActive(false);
            eventEntityRepo.save(eventEntityRepoById.get());
            return "Stopped";
        }

        return "Not stopped";
    }

}

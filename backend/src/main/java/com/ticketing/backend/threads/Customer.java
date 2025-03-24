package com.ticketing.backend.threads;

import com.ticketing.backend.entities.EventEntity;
import com.ticketing.backend.entities.TicketHistoryTable;
import com.ticketing.backend.repository.TicketHistoryRepo;

public class Customer implements Runnable {
    private TicketPool ticketPool;
    private EventEntity eventEntity;
    private TicketHistoryRepo ticketHistoryRepo;
    private int quantity;
    private int userId;

    public Customer(TicketPool ticketPool, EventEntity eventEntity, TicketHistoryRepo ticketHistoryRepo, int quantity, int userId) {
        this.ticketPool = ticketPool;
        this.eventEntity = eventEntity;
        this.ticketHistoryRepo = ticketHistoryRepo;
        this.quantity = quantity;
        this.userId = userId;
    }

    @Override
    public void run(){
        int ticketCount = 0;

        for (int i = 0; i < quantity; i++) {
            ticketPool.removeTicket();
            ticketCount++;

            try {
                Thread.sleep(eventEntity.getCustomerRetrieveRate() * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (ticketCount > 0){
            TicketHistoryTable ticketHistoryTable = new TicketHistoryTable();
            ticketHistoryTable.setUserId(userId);
            ticketHistoryTable.setEventId(eventEntity.getEventId());
            ticketHistoryTable.setQuantity(quantity);
            ticketHistoryTable.setName(eventEntity.getEventName());
            ticketHistoryTable.setTotal(quantity * eventEntity.getTicketPrice());
            ticketHistoryTable.setLocation(eventEntity.getEventLocation());
            ticketHistoryTable.setTicketPrice(eventEntity.getTicketPrice());
            ticketHistoryRepo.save(ticketHistoryTable);
        }
    }
}

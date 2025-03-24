package com.ticketing.backend.threads;

import com.ticketing.backend.entities.TicketPoolData;
import com.ticketing.backend.repository.PoolRepo;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

public class TicketPool {
    private List<Integer> ticketQueue;
    private int maximumCapacity;
    private int eventId;
    private PoolRepo poolRepo;

    public TicketPool(int maximumCapacity, int eventId, PoolRepo poolRepo) {
        this.maximumCapacity = maximumCapacity;
        this.ticketQueue = new Vector<>();
        this.eventId = eventId;
        this.poolRepo = poolRepo;
    }

    public synchronized void addTickets(int ticket){
        while (ticketQueue.size() >= maximumCapacity){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.ticketQueue.add(ticket);
        notifyAll();
        Optional<TicketPoolData> pool = poolRepo.findById(eventId);

        if (pool.isPresent()){
            pool.get().setCount(pool.get().getCount() + 1);
            pool.get().setRemaining(pool.get().getRemaining() - 1);
            poolRepo.save(pool.get());// 55%
        }

        System.out.println(Thread.currentThread().getName() + " has added a ticket to the Pool. Current size is " + ticketQueue.size());
    }

    public synchronized void removeTicket(){
        while (ticketQueue.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }

        int ticket = ticketQueue.removeFirst();
        notifyAll();

        Optional<TicketPoolData> pool = poolRepo.findById(eventId);

        if (pool.isPresent()){
            pool.get().setCount(pool.get().getCount() - 1);
            poolRepo.save(pool.get());
        }

        System.out.println(Thread.currentThread().getName() + " has bought a ticket from the pool. current size is " + ticketQueue.size() + ". Ticket is " + ticket);

    }

    public synchronized int getEventId(){
        return eventId;
    }
}


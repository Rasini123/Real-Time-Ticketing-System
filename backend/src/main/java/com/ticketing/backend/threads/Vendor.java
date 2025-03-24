package com.ticketing.backend.threads;

public class  Vendor implements Runnable {
    private TicketPool ticketPool;
    private int totalTickets;
    private int ticketReleaseRate;

    public Vendor(TicketPool ticketPool, int totalTickets, int ticketReleaseRate) {
        this.ticketPool = ticketPool;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    @Override
    public void run(){
        for (int i = 1; i < totalTickets; i++) {
            ticketPool.addTickets(i);

            try {
                Thread.sleep(ticketReleaseRate * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

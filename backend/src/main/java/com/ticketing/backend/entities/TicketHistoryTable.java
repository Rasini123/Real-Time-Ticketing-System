package com.ticketing.backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Ticket_History")
public class TicketHistoryTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int eventId;
    private String name;
    private String location;
    private int quantity;
    private double ticketPrice;
    private double total;

    private int userId;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

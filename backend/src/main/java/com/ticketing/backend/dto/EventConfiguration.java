package com.ticketing.backend.dto;

public class EventConfiguration {
    private String eventName;
    private String eventLocation;
    private String date;
    private double ticketPrice;
    private int userId;
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrieveRate;
    private int maxTicketCapacity;

    public EventConfiguration(String eventName, String eventLocation, String date, double ticketPrice, int userId, int totalTickets, int ticketReleaseRate, int customerRetrieveRate, int maxTicketCapacity) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.date = date;
        this.ticketPrice = ticketPrice;
        this.userId = userId;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrieveRate = customerRetrieveRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrieveRate() {
        return customerRetrieveRate;
    }

    public void setCustomerRetrieveRate(int customerRetrieveRate) {
        this.customerRetrieveRate = customerRetrieveRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }
}

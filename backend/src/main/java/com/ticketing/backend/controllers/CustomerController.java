package com.ticketing.backend.controllers;

import com.ticketing.backend.services.CustomerServices;
import com.ticketing.backend.services.TicketServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private TicketServices ticketServices;

    @Autowired
    private CustomerServices customerServices;

    @PostMapping("/buy-ticket/{eventId}/{quantity}/{userId}")
    public ResponseEntity<String> buyTickets(@PathVariable int eventId, @PathVariable int quantity, @PathVariable int userId){
        ticketServices.getTickets(eventId, quantity, userId);
        return ResponseEntity.ok("Buyed Tickets");
    }

    @GetMapping("/events")
    public ResponseEntity<?> getEvents(){ // retrieveEvents()
        return ResponseEntity.ok(customerServices.getAlEvents());
    }

    @GetMapping("/tickets/{userId}")
    public ResponseEntity<?> ticketHistory(@PathVariable int userId){
        return ResponseEntity.ok(customerServices.getTicketHistory(userId));
    }
}

package com.ticketing.backend.controllers;

import com.ticketing.backend.dto.EventConfiguration;
import com.ticketing.backend.services.TicketServices;
import com.ticketing.backend.services.VendorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/vendor")
public class VendorController {
    @Autowired
    private TicketServices ticketServices;

    @Autowired
    private VendorServices vendorServices;

    @PostMapping("/create-event")
    public ResponseEntity<String> createEvent(@RequestBody EventConfiguration eventConfiguration){
        boolean isCreated = ticketServices.createEvent(eventConfiguration);

        if (isCreated){
            return ResponseEntity.ok("Created !");
        }
        return ResponseEntity.status(500).build();
    }

    @GetMapping("/active-events/{userId}")
    public ResponseEntity<?> getActiveEvents(@PathVariable int userId){
        return ResponseEntity.ok(vendorServices.getAlEvents(userId));
    }

    @GetMapping("/inactive-events/{userId}")
    public ResponseEntity<?> getInactiveEvents(@PathVariable int userId){
        return ResponseEntity.ok(vendorServices.getAllInactiveEvents(userId));
    }

    @GetMapping("/pool-data/{eventId}")
    public ResponseEntity<?> poolEvent(@PathVariable int eventId){
        return ResponseEntity.ok(ticketServices.getTicketPool(eventId));
    }

    @GetMapping("/stop/{eventId}")
    public ResponseEntity<?> stopEvent(@PathVariable int eventId){
        return ResponseEntity.ok(ticketServices.stopEvent(eventId));
    }
}

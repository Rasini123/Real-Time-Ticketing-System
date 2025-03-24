package com.ticketing.backend.controllers;

import com.ticketing.backend.dto.AuthConfiguration;
import com.ticketing.backend.entities.UserEntity;
import com.ticketing.backend.services.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthServices authServices;

    @PostMapping("/new-acc")
    public ResponseEntity<?> createAccount(@RequestBody AuthConfiguration authConfiguration){
        UserEntity user = authServices.fetchUser(authConfiguration);
        return ResponseEntity.ok(user);
    }
}

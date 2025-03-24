package com.ticketing.backend.services;

import com.ticketing.backend.dto.AuthConfiguration;
import com.ticketing.backend.entities.UserEntity;
import com.ticketing.backend.repository.UserEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServices {
    @Autowired
    private UserEntityRepo userEntityRepo;

    public UserEntity fetchUser(AuthConfiguration user) {
        Optional<UserEntity> optionalUserEntity = userEntityRepo.findByEmail(user.getEmail());

        if (optionalUserEntity.isEmpty()) {
            UserEntity userData = new UserEntity();
            userData.setEmail(user.getEmail());
            userData.setPassword(user.getPassword());
            return userEntityRepo.save(userData);
        }

        if (optionalUserEntity.get().getPassword().equals(user.getPassword())) {
            return optionalUserEntity.get();
        }

        return null;
    }
}

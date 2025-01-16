package com.example.authuser.services;


import com.example.cloudywaybackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private final UserRepository userRepository;

    @Autowired
    public PasswordService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void resetPassword(String uid, String email) {

    }
}

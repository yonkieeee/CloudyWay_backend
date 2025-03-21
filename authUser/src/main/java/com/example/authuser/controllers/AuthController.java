package com.example.authuser.controllers;

import com.example.authuser.models.User;
import com.example.authuser.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try{
            if (userRepository.existById(user.getUid())) {
                return ResponseEntity.badRequest().body("User already exists");
            }

            if (userRepository.getUserBy("username", user.getUsername()) != null){
                return ResponseEntity.badRequest().body("Username already exists");
            }

            userRepository.saveUser(user);
            return ResponseEntity.ok("User created");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getUserById(@RequestParam(value="uid") String uid) {
        try{
            if (userRepository.existById(uid)) {
                return ResponseEntity.ok(userRepository.getUser(uid));
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return null;
    }
}

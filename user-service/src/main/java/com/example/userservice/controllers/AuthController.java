package com.example.userservice.controllers;

import com.example.userservice.models.User;
import com.example.userservice.repositories.UserRepository;
import com.example.userservice.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UserRepository userRepository;

    @Autowired
    private S3Service s3Service;

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

    @PutMapping
    public ResponseEntity<?> putPhoto(@RequestParam(value="uid") String uid,
                                      @RequestParam(value="profileImage")MultipartFile file) throws IOException {
        try {

            User user = userRepository.getUserBy("uid", uid).orElse(null);

            if(getUserById(uid) == null){
                return ResponseEntity.badRequest().body("User not found");
            }

            if(user.getProfileImageUrl() != null){
                s3Service.deleteObjectByUrl(user.getProfileImageUrl());
            }
            String fileName = file.getOriginalFilename();

            String key = "profileImage/" + uid + "/" + fileName;

            var putPhoto = s3Service.PutObject(key, file);

            user.setProfileImageUrl(putPhoto);

            userRepository.saveUser(user);

            return ResponseEntity.ok().body(putPhoto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
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

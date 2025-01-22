package com.example.authuser.controllers;

import com.example.authuser.models.User;
import com.example.authuser.repositories.UserRepository;
import com.example.authuser.services.ForgetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/reset-password")
@CrossOrigin("*")
public class ResetPasswordController {
    private final ForgetPasswordService forgetPasswordService;
    private final UserRepository userRepository;

    @Autowired
    public ResetPasswordController(ForgetPasswordService forgetPasswordService,
                                   UserRepository userRepository) {
        this.userRepository = userRepository;
        this.forgetPasswordService = forgetPasswordService;
    }
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOTP(@RequestBody Map<String, String> body)
            throws ExecutionException, InterruptedException {

        User user;
        String identifier = body.get("identifier");

        if(identifier.contains("@")){
            user = userRepository.getUserBy("email", identifier);
        } else {
            user = userRepository.getUserBy("username", identifier);
        }

        if(user != null){
            try {
                forgetPasswordService.sendOTP(user.getEmail());
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
            return ResponseEntity.ok().body("OTP sent successfully");
        } else{
            return ResponseEntity.ok().body("User not exists");
        }
    }
    @PostMapping("/validate-otp")
    public ResponseEntity<?> validateOTP(@RequestBody Map<String, String> body)
            throws ExecutionException, InterruptedException{

        User user;
        String identifier = body.get("identifier");
        String otp = body.get("otp");

        if(identifier.contains("@")){
            user = userRepository.getUserBy("email", identifier);
        } else {
            user = userRepository.getUserBy("username", identifier);
        }
        if(user == null) return ResponseEntity.badRequest().body("User not finded");

        if(forgetPasswordService.validateOTP(user.getEmail(), otp)){
            return ResponseEntity.ok().body("OTP verified");
        } else {
            return ResponseEntity.badRequest().body("OTP not verified");
        }
    }

    @PutMapping
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, Object> body)
            throws ExecutionException, InterruptedException {
        User user;
        String identifier = body.get("identifier").toString();
        String password = body.get("password").toString();

        if(identifier.contains("@")){
            user = userRepository.getUserBy("email", identifier);
        } else {
            user = userRepository.getUserBy("username", identifier);
        }

        if(user == null) return ResponseEntity.badRequest().body("User not exists");

        body.put("password",
                BCrypt.hashpw((String) body.get("password"), BCrypt.gensalt()));
        try {
            userRepository.changeUser(user.getUid(), body);
            return ResponseEntity.ok().body("Password changed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}


package org.example.postservice.controllers;

import org.example.postservice.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/post")
@CrossOrigin("*")
public class PostController {
    private final S3Service s3Service;

    @Autowired
    public PostController(final S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PutMapping
    public ResponseEntity<?> putPhoto(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();

            String key = "posts/" + fileName;

            s3Service.PutObject(key, file);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

package org.example.postservice.controllers;

import org.example.postservice.Repository.PostRepo;
import org.example.postservice.dto.RequestPost;
import org.example.postservice.models.Post;
import org.example.postservice.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@CrossOrigin("*")
public class PostController {
    private final S3Service s3Service;
    private final PostRepo postRepo;

    @Autowired
    public PostController(S3Service s3Service, PostRepo postRepo) {
        this.s3Service = s3Service;
        this.postRepo = postRepo;
    }

    @PutMapping
    public ResponseEntity<?> putPhoto(@RequestParam("UID") String uid,
                                      @ModelAttribute RequestPost requestPost) {
        try {
            String fileName = requestPost.file().getOriginalFilename();

            String key = "posts/" + uid + "/" + fileName;

            var putPhoto = s3Service.PutObject(key, requestPost.file());

            Post post = new Post(
                    String.valueOf((int)(Math.random() * 900000)),
                    putPhoto,
                    requestPost.coordinates(),
                    requestPost.description()
            );

            postRepo.addPost(uid, post);

            return ResponseEntity.ok().body(putPhoto);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts(@RequestParam("uid") String uid) {
        try{
            return ResponseEntity.ok(postRepo.getPosts(uid));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

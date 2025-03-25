package org.example.postservice.Repository;

import org.example.postservice.models.Post;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.example.postservice.models.UserPosts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Repository
public class PostRepo {
    private final CollectionReference postsCollection;

    @Autowired
    public PostRepo(Firestore firestore) {
        this.postsCollection = firestore.collection("posts");
    }

    public void addPost(String uid, Post post) throws ExecutionException, InterruptedException {
        DocumentReference docRef = postsCollection.document(uid);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        UserPosts userPosts;
        if (document.exists()) {
            userPosts = document.toObject(UserPosts.class);

            assert userPosts != null;
            if (userPosts.getPosts() == null) {
                userPosts.setPosts(new ArrayList<>());
            }
        } else {
            userPosts = new UserPosts();
            userPosts.setId(uid);
            userPosts.setPosts(new ArrayList<>());
        }

        userPosts.addPost(post);

        ApiFuture<WriteResult> writeFuture = docRef.set(userPosts);
        writeFuture.get();
    }

    public List<Post> getPosts(String uid) throws ExecutionException, InterruptedException {
        DocumentReference docRef = postsCollection.document(uid);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            UserPosts userPosts = document.toObject(UserPosts.class);
            if (userPosts != null) {
                return userPosts.getPosts();
            }
        }
        return null;
    }

    public List<Post> getPost(String uid, String postId) throws ExecutionException, InterruptedException {
        DocumentReference docRef = postsCollection.document(uid);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (!document.exists())
            return Collections.emptyList();

        UserPosts userPosts = document.toObject(UserPosts.class);

        if (userPosts == null || userPosts.getPosts() == null)
            return Collections.emptyList();

        return userPosts.getPosts().stream()
                .filter(post -> Objects.equals(post.getPostID(), postId))
                .collect(Collectors.toList());
    }
}

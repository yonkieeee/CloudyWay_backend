package org.example.postservice.models;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Document(collectionName = "posts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserPosts {
    @DocumentId
    private String id;

    private List<Post> posts;

    public void addPost(Post post) {
        posts.add(post);
    }

    public void removePost(String postId) {
        posts.removeIf(post -> post.getPostID().equals(postId));
    }
}

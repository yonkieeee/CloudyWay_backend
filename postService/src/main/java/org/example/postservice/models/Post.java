package org.example.postservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Post {
    private String postID;

    private String imageUrl;

    private String coordinates;

    private String description;
}

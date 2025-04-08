package org.example.postservice.models;

import com.google.cloud.spring.data.firestore.Document;
import lombok.*;

@Document
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Like {
    public String postId;
    public String userId;

}

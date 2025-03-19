package com.example.authuser.models;

import com.google.cloud.spring.data.firestore.Document;
import com.google.firebase.database.annotations.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;


@Setter @Getter
@Document(collectionName="users")
public class User {

    @Id
    @NotNull
    private String uid;

    private String fyufu;
    private String username;
    private String email;
    private String dateOfBirth;
    private String gender;
    private String region;

    public User(){}

    public User(String uid, String username, String email, String dateOfBirth, String gender, String region) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.region = region;
    }
}

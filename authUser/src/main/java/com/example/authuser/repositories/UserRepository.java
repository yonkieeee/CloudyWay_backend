package com.example.authuser.repositories;

import com.example.authuser.models.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public class UserRepository{

    private final CollectionReference usersCollection;

    @Autowired
    public UserRepository(Firestore firestore) {
        this.usersCollection = firestore.collection("users"); // Use the collection name
    }

    public void saveUser(User user)
            throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = usersCollection.document(user.getUid()).set(user);
        future.get();
    }

    public Optional<User> getUser(String uid)
            throws ExecutionException, InterruptedException {
        DocumentReference docRef = usersCollection.document(uid);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if(document.exists()) {
            return Optional.ofNullable(document.toObject(User.class));
        }
        return Optional.empty();
    }

    public User getUserBy(String field, String value)
            throws ExecutionException, InterruptedException {
        Query query = usersCollection.whereEqualTo(field, value);
        ApiFuture<QuerySnapshot> future = query.get();

        QuerySnapshot querySnapshot = future.get();

        if (querySnapshot != null && !querySnapshot.isEmpty()) {

            DocumentSnapshot document = querySnapshot.getDocuments().get(0);
            return document.toObject(User.class);
        }

        return null;
    }

    public Boolean existById(String uid)
            throws ExecutionException, InterruptedException {
        DocumentReference docRef = usersCollection.document(uid);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        return document.exists();
    }

    public void changeUser(String uid, Map<String, Object> updates)
            throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = usersCollection.document(uid).update(updates);
        future.get();
    }
}

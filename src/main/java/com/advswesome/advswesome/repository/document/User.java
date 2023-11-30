package com.advswesome.advswesome.repository.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Document(collectionName = "users")
public class User {

    @DocumentId
    private String userId;
    private String clientId; // Provided by services/apps, not the human end-users

    private String email;
    private String username;
    private String password;
    private String role;
    
    private String createdAt; // Provided by services/apps, not the human end-users
    private String updatedAt; // Provided by services/apps, not the human end-users

}
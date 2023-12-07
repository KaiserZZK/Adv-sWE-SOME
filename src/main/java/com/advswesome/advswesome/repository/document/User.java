package com.advswesome.advswesome.repository.document;

import lombok.*;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;

@NoArgsConstructor
@AllArgsConstructor
@Document(collectionName = "users")
@Getter
@Setter
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

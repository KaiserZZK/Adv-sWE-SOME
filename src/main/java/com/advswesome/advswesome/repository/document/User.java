package com.advswesome.advswesome.repository.document;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Document(collectionName = "users")
public class User {
    @DocumentId
    private String userId;

    private String clientId; // Provided by services/apps, but the human end-users

    private String username;
    private String password;
    private String email;

    private String createdAt; // Provided by services/apps, but the human end-users
    private String updatedAt; // Provided by services/apps, but the human end-users

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStoredHash() {
        return password;
    }

    public String getPassword() {
        return password;
    }

    // Method to hash a password and set the hashed value
    public void setPassword(String password) {
        // Hash the password (without a salt)
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.password =  bCryptPasswordEncoder.encode(password); 
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}

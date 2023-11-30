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

// import com.google.cloud.firestore.annotation.DocumentId;
// import com.google.cloud.spring.data.firestore.Document;
// import java.util.Collection;
// import java.util.List;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

// @Document(collectionName = "users")
// public class User implements UserDetails {
//     @DocumentId
//     private String userId;
//     private String clientId; // Provided by services/apps, but the human end-users
//     private Role role;
//     private String username;
//     private String password;
//     private String email;
//     private String createdAt; // Provided by services/apps, but the human end-users
//     private String updatedAt; // Provided by services/apps, but the human end-users

//     public String getUserId() {
//         return userId;
//     }

//     public void setUserId(String userId) {
//         this.userId = userId;
//     }

//     public String getClientId() {
//         return clientId;
//     }

//     public void setClientId(String clientId) {
//         this.clientId = clientId;
//     }

//     public Role getRole() {
//         return role;
//     }

//     public void setRole(Role role) {
//         this.role = role; 
//     }

//     // refactored to getAccountname to differentiate from the method that overrides 
//     public String getAccountname() {
//         return username;
//     }

//     // overriding UserDetails
//     @Override
//     public String getUsername() {
//         return email;
//     }

//     public void setUsername(String username) {
//         this.username = username;
//     }

//     public String getPassword() {
//         return password;
//     }

//     public void setPassword(String password) {
//         this.password =  password; 
//     }

//     public String getEmail() {
//         return email;
//     }

//     public void setEmail(String email) {
//         this.email = email;
//     }

//     public String getCreatedAt() {
//         return createdAt;
//     }

//     public void setCreatedAt(String createdAt) {
//         this.createdAt = createdAt;
//     }

//     public String getUpdatedAt() {
//         return updatedAt;
//     }

//     public void setUpdatedAt(String updatedAt) {
//         this.updatedAt = updatedAt;
//     }

//     // for authentication and authorization
//     @Override
//     public Collection<? extends GrantedAuthority> getAuthorities() {
//         return List.of(new SimpleGrantedAuthority(role.name()));
//     }

//     @Override
//     public boolean isAccountNonExpired() {
//         return true;
//     }

//     @Override
//     public boolean isAccountNonLocked() {
//         return true;
//     }

//     @Override
//     public boolean isCredentialsNonExpired() {
//         return true;
//     }

//     @Override
//     public boolean isEnabled() {
//         return true;
//     }

// }

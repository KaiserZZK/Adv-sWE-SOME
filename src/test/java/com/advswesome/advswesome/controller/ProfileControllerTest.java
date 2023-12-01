package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Profile;
import com.advswesome.advswesome.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


import static org.mockito.Mockito.*;

public class ProfileControllerTest {

    @InjectMocks
    private ProfileController profileController;

    @Mock
    private ProfileService profileService;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(profileController).build();
    }

    // @Test
    // void createProfile() {
    //     Profile profile = new Profile();
    //     profile.setProfileId(String.valueOf(123));
    //     profile.setAge(23);
    //     when(profileService.createProfile(profile)).thenReturn(Mono.just(profile));

    //     webTestClient.post()
    //             .uri("/profiles")
    //             .bodyValue(profile)
    //             .exchange()
    //             .expectStatus().isOk()
    //             .expectBody(Profile.class);
    // }

    @Test
    void getProfileById() {
        Profile profile = new Profile();
        profile.setProfileId("3959");
        when(profileService.getProfileById("3959")).thenReturn(Mono.just(profile));

        webTestClient.get()
                .uri("/profiles/3959")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Profile.class);
    }

    @Test
    void updateProfile() {
        Profile profile = new Profile();
        profile.setProfileId("3959");
        when(profileService.getProfileById("3959")).thenReturn(Mono.just(profile));
        when(profileService.updateProfile(any(Profile.class))).thenReturn(Mono.just(profile));

        webTestClient.put()
                .uri("/profiles/3959")
                .bodyValue(profile)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Profile.class);
    }

    @Test
    void updateProfileNotExist() {
        when(profileService.getProfileById("3959")).thenReturn(Mono.empty());

        webTestClient.put()
                .uri("/profiles/3959")
                .bodyValue(new Profile())
                .exchange()
                .expectStatus().isNotFound();
    }


    @Test
    void deleteProfile() {
        Profile profile = new Profile();
        profile.setProfileId("3959");
        when(profileService.getProfileById("3959")).thenReturn(Mono.just(profile));
        when(profileService.deleteProfile("3959")).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/profiles/3959")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void deleteProfileNotExist() {
        when(profileService.getProfileById("3959")).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/profiles/3959")
                .exchange()
                .expectStatus().isNotFound();
    }

//    @Test
//    void createProfileWithInvalidData() {
//        Profile invalidProfile = new Profile();
//
//        //
//        // Throw an exception from the profileService when given invalid data
//        when(profileService.createProfile(invalidProfile)).thenThrow(new IllegalArgumentException());
//        // OR
//        // Return a custom error response
//
//        // Validate that an exception is thrown OR the response matches your error response.
//        assertThrows(IllegalArgumentException.class, () -> profileController.createProfile(invalidProfile).block());
//    }

    //TODO: Add Test Profile Not Found Exception
//    @Test
//    void getNonExistentProfileById() {
//        String nonExistentId = "1000"; // An ID that doesn't exist in your database
//        when(profileService.getProfileById(nonExistentId)).thenReturn(Mono.empty());
//
//        // Here, you can either expect an exception OR a custom not found response
//        assertThrows(ProfileNotFoundException.class, () -> profileController.getProfileById(nonExistentId).block());
//    }



    //TODO: MOCK INVALID INPUT, NEED TO IMPLEMENT IT
//    @Test
//    void createProfileWithInvalidAge() {
//        Profile invalidProfile = new Profile();
//        invalidProfile.setAge(-5); // Setting an invalid age
//
//        when(profileService.createProfile(invalidProfile)).thenThrow(new IllegalArgumentException("Age cannot be negative"));
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> profileController.createProfile(invalidProfile).block());
//
//        assertTrue(exception.getMessage().contains("Age cannot be negative"));
//    }

    //TODO: MOCK INVALID INPUT, NEED TO IMPLEMENT IT
//    @Test
//    void createProfileWithMissingName() {
//        Profile invalidProfile = new Profile();
//        // Assuming name is a mandatory field, not setting it should make the profile invalid.
//
//        when(profileService.createProfile(invalidProfile)).thenThrow(new IllegalArgumentException("Name field is mandatory"));
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> profileController.createProfile(invalidProfile).block());
//
//        assertTrue(exception.getMessage().contains("Name field is mandatory"));
//    }



}

package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Profile;
import com.advswesome.advswesome.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
public class ProfileControllerTest {

    @InjectMocks // Inject the mock objects into the instance of the class
    private ProfileController profileController;


    @Mock // Mock the ProfileService which is a dependency for ProfileController
    private ProfileService profileService;

    @BeforeEach // Setting up mocks
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test // Test to ensure a profile can be created through the controller
    void createProfile() {
        Profile profile = new Profile();
        // set the profile fields e.g., profile.setAge(25);

        // When createProfile of profileService is called, return the same profile.
        when(profileService.createProfile(profile)).thenReturn(Mono.just(profile));

        // createProfile from the controller
        profileController.createProfile(profile).block();

        // Verify that the createProfile method of profileService was called exactly once.
        verify(profileService, times(1)).createProfile(profile);
    }

    @Test // Test to ensure that a profile can be retrieved by its ID through the controller
    void getProfileById() {
        Profile profile = new Profile();

        // set the profile fields
        profile.setProfileId("3959");
        when(profileService.getProfileById("3959")).thenReturn(Mono.just(profile));

        // getProfileById from the controller
        profileController.getProfileById("3959").block();

        // Verify that the getProfileById method of profileService was called exactly once with ID "3959".
        verify(profileService, times(1)).getProfileById("3959");
    }

    @Test  // Test to ensure a profile can be updated through the controller
    void updateProfile() {
        Profile profile = new Profile();
        profile.setProfileId("3959");
        when(profileService.getProfileById("3959")).thenReturn(Mono.just(profile));
        when(profileService.updateProfile(profile)).thenReturn(Mono.just(profile));

        profileController.updateProfile("3959", profile).block();

        // Verify that the updateProfile method of profileService was called exactly once.
        verify(profileService, times(1)).updateProfile(profile);
    }


    @Test // Test to ensure a profile can be deleted through the controller
    void deleteProfile() {
        when(profileService.deleteProfile("3959")).thenReturn(Mono.empty());

        profileController.deleteProfile("3959");

        // Verify that the deleteProfile method of profileService was called exactly once with ID "3959".
        verify(profileService, times(1)).deleteProfile("3959");
    }

    @Test
    void createProfileWithInvalidData() {
        Profile invalidProfile = new Profile();

        //
        // Throw an exception from the profileService when given invalid data
        when(profileService.createProfile(invalidProfile)).thenThrow(new IllegalArgumentException());
        // OR
        // Return a custom error response

        // Validate that an exception is thrown OR the response matches your error response.
        assertThrows(IllegalArgumentException.class, () -> profileController.createProfile(invalidProfile).block());
    }

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
    @Test
    void createProfileWithInvalidAge() {
        Profile invalidProfile = new Profile();
        invalidProfile.setAge(-5); // Setting an invalid age

        when(profileService.createProfile(invalidProfile)).thenThrow(new IllegalArgumentException("Age cannot be negative"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> profileController.createProfile(invalidProfile).block());

        assertTrue(exception.getMessage().contains("Age cannot be negative"));
    }

    //TODO: MOCK INVALID INPUT, NEED TO IMPLEMENT IT
    @Test
    void createProfileWithMissingName() {
        Profile invalidProfile = new Profile();
        // Assuming name is a mandatory field, not setting it should make the profile invalid.

        when(profileService.createProfile(invalidProfile)).thenThrow(new IllegalArgumentException("Name field is mandatory"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> profileController.createProfile(invalidProfile).block());

        assertTrue(exception.getMessage().contains("Name field is mandatory"));
    }



}

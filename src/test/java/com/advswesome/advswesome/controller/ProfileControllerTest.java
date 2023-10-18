package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Profile;
import com.advswesome.advswesome.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

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

}

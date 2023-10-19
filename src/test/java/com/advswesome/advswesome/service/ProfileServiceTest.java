package com.advswesome.advswesome.service;

import com.advswesome.advswesome.repository.ProfileRepository;
import com.advswesome.advswesome.repository.document.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProfileServiceTest {

    @InjectMocks
    private ProfileService profileService;

    @Mock
    private ProfileRepository profileRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProfile() {
        Profile profile = new Profile();

        // When the 'save' method of the mocked ProfileRepository is called with profile,
        // instruct Mockito to return a Mono with our profile.
        when(profileRepository.save(profile)).thenReturn(Mono.just(profile));

        profileService.createProfile(profile).block();

        // Verifying that the 'save' method of ProfileRepository is called exactly once
        verify(profileRepository, times(1)).save(profile);
    }

    @Test
    void getProfileById() {
        Profile profile = new Profile();
        // set the profile fields
        profile.setProfileId("3959");

        // When the 'findById' method of the mocked ProfileRepository is called with "3959",
        // instruct Mockito to return a Mono with profile.
        when(profileRepository.findById("3959")).thenReturn(Mono.just(profile));

        profileService.getProfileById("3959").block();

        // Verifying that the 'save' method of ProfileRepository is called exactly once
        verify(profileRepository, times(1)).findById("3959");
    }

    @Test
    void updateProfile() {
        Profile profile = new Profile();
        // set the profile fields
        profile.setProfileId("3959");

        // Since the 'updateProfile' method in the service internally uses the 'save' method
        // of the repository for both creating and updating, mock the save method here.
        when(profileRepository.save(profile)).thenReturn(Mono.just(profile));

        profileService.updateProfile(profile).block();

        // Verifying that the 'save' method of ProfileRepository is called exactly once
        verify(profileRepository, times(1)).save(profile);
    }

    @Test
    void deleteProfile() {

        // instruct Mockito to return an empty Mono to simulate a successful deletion.
        when(profileRepository.deleteById("3959")).thenReturn(Mono.empty());

        profileService.deleteProfile("3959").block();

        // Verifying that the 'deleteById' method of ProfileRepository is called exactly once
        verify(profileRepository, times(1)).deleteById("3959");
    }

    // TODO: Test Saving Invalid Profile:
//    @Test
//    void createInvalidProfile() {

//    }

    // TODO: Test Retrieving Non-Existent Profile:
//    @Test
//    void getNonExistentProfile() {
//    }

    // TODO: Test Handling of DB Exceptions:
//    @Test
//    void handleDatabaseExceptionDuringCreation() {
//
//    }



}

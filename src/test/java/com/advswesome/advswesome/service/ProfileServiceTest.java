package com.advswesome.advswesome.service;

import com.advswesome.advswesome.repository.ProfileRepository;
import com.advswesome.advswesome.repository.document.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
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
        profile.setProfileId("3959");
        profile.setUserId("user1");
        profile.setAge(25);
        profile.setSex("Male");
        profile.setLocation("US");
        profile.setPhysicalFitness("Good");
        profile.setMedicalHistory(Arrays.asList(new Profile.MedicalHistory("Flu", "2020-02-20", "Rest")));

        when(profileRepository.save(profile)).thenReturn(Mono.just(profile));

        Profile savedProfile = profileService.createProfile(profile).block();

        assertNotNull(savedProfile);
        assertEquals(profile.getProfileId(), savedProfile.getProfileId());

        verify(profileRepository, times(1)).save(profile);
    }

    @Test
    void getProfileById() {
        Profile profile = new Profile();
        profile.setProfileId("3959");
        profile.setAge(25);

        when(profileRepository.findById("3959")).thenReturn(Mono.just(profile));

        Profile fetchedProfile = profileService.getProfileById("3959").block();

        assertNotNull(fetchedProfile);
        assertEquals(fetchedProfile.getProfileId(), profile.getProfileId());

        verify(profileRepository, times(1)).findById("3959");
    }

    @Test
    void updateProfile() {
        Profile profile = new Profile();
        profile.setProfileId("3959");
        profile.setAge(25);

        when(profileRepository.findById("3959")).thenReturn(Mono.just(profile));
        when(profileRepository.save(any(Profile.class))).thenReturn(Mono.just(profile));

        profile.setAge(26);
        Profile updatedProfile = profileService.updateProfile(profile).block();

        assertNotNull(updatedProfile);
        assertEquals(updatedProfile.getAge(), 26);

        verify(profileRepository, times(1)).save(profile);
    }

    @Test
    void deleteProfile() {
        Profile profile = new Profile();
        profile.setProfileId("3959");

        when(profileRepository.findById("3959")).thenReturn(Mono.just(profile));
        when(profileRepository.deleteById("3959")).thenReturn(Mono.empty());

        profileService.deleteProfile("3959").block();

        verify(profileRepository, times(1)).deleteById("3959");
    }

    @Test
    void getAllProfilesForUser() {
        Profile profile1 = new Profile();
        profile1.setProfileId("3959");
        profile1.setUserId("user1");
        Profile profile2 = new Profile();
        profile2.setProfileId("4000");
        profile2.setUserId("user1");

        when(profileRepository.findByUserId("user1")).thenReturn(Flux.just(profile1,profile2));

        Flux<Profile> profiles = profileService.getProfilesByUserId("user1");

        // verify each item in the returning prescriptions
        StepVerifier.create(profiles)
                .expectNext(profile1)
                .expectNext(profile2)
                .verifyComplete();

        verify(profileRepository, times(1)).findByUserId("user1");


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

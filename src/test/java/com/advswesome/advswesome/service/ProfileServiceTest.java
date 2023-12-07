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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        Profile.MedicalHistory hist = new Profile.MedicalHistory();
        hist.setDiseaseName("dd");
        hist.setDiagnosedAt("123");
        hist.setTreatment("none");
        List<Profile.MedicalHistory> ml= new ArrayList<>();
        ml.add(hist);

        profile.setMedicalHistory(ml);

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
    void getNonExistentProfile() {
        when(profileRepository.findById("nonexistent")).thenReturn(Mono.empty());

        Mono<Profile> result = profileService.getProfileById("nonexistent");

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        verify(profileRepository, times(1)).findById("nonexistent");
    }

    @Test
    void handleDatabaseExceptionDuringCreation() {
        Profile profile = new Profile();
        profile.setProfileId("3959");

        when(profileRepository.save(profile)).thenReturn(Mono.error(new RuntimeException("Database error")));

        Mono<Profile> result = profileService.createProfile(profile);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && throwable.getMessage().equals("Database error"))
                .verify();

        verify(profileRepository).save(profile);
    }

    @Test
    void getAllProfilesForUser() {
        Profile profile1 = new Profile();
        profile1.setProfileId("3959");
        profile1.setUserId("user1");
        Profile profile2 = new Profile();
        profile2.setProfileId("4000");
        profile2.setUserId("user1");

        when(profileRepository.findByUserId("user1")).thenReturn(Flux.just(profile1, profile2));

        Flux<Profile> profiles = profileService.getProfilesByUserId("user1");

        StepVerifier.create(profiles)
                .expectNext(profile1)
                .expectNext(profile2)
                .verifyComplete();

        verify(profileRepository, times(1)).findByUserId("user1");
    }

    @Test
    void createInvalidProfile() {
        Profile invalidProfile = new Profile();

        when(profileRepository.save(invalidProfile)).thenReturn(Mono.error(new IllegalArgumentException("Invalid profile")));

        Mono<Profile> result = profileService.createProfile(invalidProfile);

        StepVerifier.create(result)
                .expectError(IllegalArgumentException.class)
                .verify();

        verify(profileRepository).save(invalidProfile);
    }

    @Test
    void updateWithInvalidData() {
        Profile invalidProfile = new Profile();

        when(profileRepository.findById(invalidProfile.getProfileId())).thenReturn(Mono.just(invalidProfile));
        when(profileRepository.save(invalidProfile)).thenReturn(Mono.error(new IllegalArgumentException("Invalid profile data")));

        Mono<Profile> result = profileService.updateProfile(invalidProfile);

        StepVerifier.create(result)
                .expectError(IllegalArgumentException.class)
                .verify();

        verify(profileRepository).save(invalidProfile);
    }

    @Test
    void handleDatabaseExceptionDuringUpdate() {
        Profile profile = new Profile();
        // Set profile details...
        when(profileRepository.save(profile)).thenReturn(Mono.error(new RuntimeException("Database error")));

        Mono<Profile> result = profileService.updateProfile(profile);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();

        verify(profileRepository).save(profile);
    }


    @Test
    void handleExceptionDuringDelete() {
        String profileId = "nonexistent";
        when(profileRepository.deleteById(profileId)).thenReturn(Mono.error(new RuntimeException("Deletion error")));

        Mono<Void> result = profileService.deleteProfile(profileId);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();

        verify(profileRepository).deleteById(profileId);
    }

}

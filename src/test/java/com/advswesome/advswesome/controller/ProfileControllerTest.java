package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Profile;
import com.advswesome.advswesome.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import static org.mockito.Mockito.*;

public class ProfileControllerTest {

    @InjectMocks
    private ProfileController profileController;

    @Mock
    private ProfileService profileService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(profileController).build();
    }

    @Test
    void createProfile() {
        Profile profile = new Profile();
        profile.setProfileId("123");
        profile.setAge(23);


        when(profileService.createProfile(any(Profile.class))).thenReturn(Mono.just(profile));

        try {
            mockMvc.perform(post("/profiles")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(profile)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(profile)));
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }


    }

    @Test
    void getProfileById() {
        Profile profile = new Profile();
        profile.setProfileId("3959");
        when(profileService.getProfileById("3959")).thenReturn(Mono.just(profile));

        try {
        mockMvc.perform(get("/profiles/3959"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(profile)));
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    void updateProfile() {
        Profile profile = new Profile();
        profile.setProfileId("3959");
        when(profileService.getProfileById("3959")).thenReturn(Mono.just(profile));
        when(profileService.updateProfile(any(Profile.class))).thenReturn(Mono.just(profile));

        try {
            mockMvc.perform(put("/profiles/3959")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(profile)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(profile)));
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    void updateProfileNotExist() {
        when(profileService.getProfileById("3959")).thenReturn(Mono.empty());

        try {
            mockMvc.perform(put("/profiles/3959")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(new Profile())))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    void deleteProfile() {
        Profile profile = new Profile();
        profile.setProfileId("3959");
        when(profileService.getProfileById("3959")).thenReturn(Mono.just(profile));
        when(profileService.deleteProfile("3959")).thenReturn(Mono.empty());

        try {
            mockMvc.perform(delete("/profiles/3959"))
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
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

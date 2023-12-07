package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Consent;
import com.advswesome.advswesome.repository.document.Profile;
import com.advswesome.advswesome.security.JwtIssuer;
import com.advswesome.advswesome.service.ConsentService;
import com.advswesome.advswesome.service.ProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ProfileControllerTest {
    @MockBean
    private ProfileService profileService;

    @MockBean
    private ConsentService consentService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private JwtIssuer jwtIssuer;

    @Autowired
    private WebApplicationContext context;

    private String token;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()) // Apply Spring Security configuration
                .build();
        try {
             token = jwtIssuer.issue(JwtIssuer.Request.builder()
                    .userId("12")
                    .email("")
                    .roles(Arrays.asList("ROLE_USER"))
                    .build());
        } catch (Exception e) {
            fail();
        }

    }

    @Test
    void createProfile() {
        Profile profile = new Profile();
        profile.setUserId("12");
        profile.setProfileId("123");
        profile.setAge(23);

        when(profileService.createProfile(any(Profile.class))).thenReturn(Mono.just(profile));

        try {
            mockMvc.perform(post("/profiles")
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(profile)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(profile)));
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    void createProfile_Forbidden() {
        Profile profile = new Profile();
        profile.setUserId("123");

        try {
            // Mock the token with a different user ID
            String anotherToken  = jwtIssuer.issue(JwtIssuer.Request.builder()
                    .userId("1")
                    .email("")
                    .roles(Arrays.asList("ROLE_USER"))
                    .build());

            mockMvc.perform(post("/profiles")
                            .header("Authorization", "Bearer " + anotherToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(profile)))
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }


    @Test
    void getProfileById() {
        Profile profile = new Profile();
        profile.setProfileId("3959");
        profile.setUserId("12");
        when(profileService.getProfileById("3959")).thenReturn(Mono.just(profile));
        when(consentService.getConsentByProfileId("3959")).thenReturn(Mono.empty());

        try {
            mockMvc.perform(get("/profiles/3959").header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(profile)));
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    void getProfileById_Forbidden() {
        Profile profile = new Profile();
        profile.setProfileId("3959");
        profile.setUserId("12");
        when(profileService.getProfileById("3959")).thenReturn(Mono.just(profile));
        when(consentService.getConsentByProfileId("3959")).thenReturn(Mono.empty());


        try {
            String anotherToken  = jwtIssuer.issue(JwtIssuer.Request.builder()
                    .userId("1")
                    .email("")
                    .roles(Arrays.asList("ROLE_USER"))
                    .build());
            mockMvc.perform(get("/profiles/3959").header("Authorization", "Bearer " + anotherToken))
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    void getProfileById_NotFound() {
        when(consentService.getConsentByProfileId("nonexistent-profile-id")).thenReturn(Mono.empty());
        when(profileService.getProfileById("nonexistent-profile-id")).thenReturn(Mono.empty());

        try {
            mockMvc.perform(get("/profiles/nonexistent-profile-id").header("Authorization", "Bearer " + token))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    void updateProfile() {
        Profile profile = new Profile();
        profile.setUserId("12");
        profile.setProfileId("3959");
        when(profileService.getProfileById("3959")).thenReturn(Mono.just(profile));
        when(profileService.updateProfile(any(Profile.class))).thenReturn(Mono.just(profile));

        try {
            mockMvc.perform(put("/profiles/3959")
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(profile)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(profile)));
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    void updateProfile_AccessDeniedException() {
        Profile profile = new Profile();
        profile.setUserId("12");
        profile.setProfileId("3959");
        when(profileService.getProfileById("3959")).thenReturn(Mono.just(profile));

        try {
            String anotherToken  = jwtIssuer.issue(JwtIssuer.Request.builder()
                    .userId("1")
                    .email("")
                    .roles(Arrays.asList("ROLE_USER"))
                    .build());
            mockMvc.perform(put("/profiles/3959")
                            .header("Authorization", "Bearer " + anotherToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(profile)))
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    void updateProfile_NotFound() {
        when(profileService.getProfileById("nonexistent-profile-id")).thenReturn(Mono.empty());

        try {
            mockMvc.perform(put("/profiles/nonexistent-profile-id")
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(new Profile())))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    void updateProfileNotExist() {
        when(profileService.getProfileById("3959")).thenReturn(Mono.empty());

        try {
            mockMvc.perform(put("/profiles/3959")
                            .header("Authorization", "Bearer " + token)
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
        profile.setUserId("12");
        profile.setProfileId("3959");
        when(profileService.getProfileById("3959")).thenReturn(Mono.just(profile));
        when(profileService.deleteProfile("3959")).thenReturn(Mono.empty());

        try {
            mockMvc.perform(delete("/profiles/3959").header("Authorization", "Bearer " + token))
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    void deleteProfile_UserNotOwner_Forbidden() {
        Profile profile = new Profile();
        profile.setUserId("otherUserId"); // Different user ID
        profile.setProfileId("3959");

        when(profileService.getProfileById("3959")).thenReturn(Mono.just(profile));

        try {
            mockMvc.perform(delete("/profiles/3959").header("Authorization", "Bearer " + token))
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }


    @Test
    void deleteProfile_NotFound() {
        when(profileService.getProfileById("nonexistent-profile-id")).thenReturn(Mono.empty());

        try {
            mockMvc.perform(delete("/profiles/nonexistent-profile-id").header("Authorization", "Bearer " + token))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    void updateProfile_UserNotOwnerAndNoConsent_Forbidden() {
        Profile profile = new Profile();
        profile.setUserId("differentUserId"); // Set a different user ID
        profile.setProfileId("3959");

        // Assume the profile exists
        when(profileService.getProfileById("3959")).thenReturn(Mono.just(profile));

        // Assume no consent exists for this user
        when(consentService.getConsentByProfileId("3959")).thenReturn(Mono.empty());

        try {
            // Use a token with a user ID different from the profile's user ID
            String anotherToken  = jwtIssuer.issue(JwtIssuer.Request.builder()
                    .userId("1") // Different user ID
                    .email("")
                    .roles(Arrays.asList("ROLE_USER"))
                    .build());

            mockMvc.perform(put("/profiles/3959")
                            .header("Authorization", "Bearer " + anotherToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(profile)))
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    void getProfileById_UserHasConsent() {
        Profile profile = new Profile();
        profile.setProfileId("3959");
        profile.setUserId("otherUserId"); // Different user ID

        Consent consent = new Consent("consentId", "12", "3959", true, new Date());

        when(profileService.getProfileById("3959")).thenReturn(Mono.just(profile));
        when(consentService.getConsentByProfileId("3959")).thenReturn(Mono.just(consent));

        try {
            mockMvc.perform(get("/profiles/3959").header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(profile)));
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    void getProfilesByUserId_ProfilesFound() {
        List<Profile> profiles = Arrays.asList(new Profile(), new Profile()); // Create sample profiles
        when(profileService.getProfilesByUserId("12")).thenReturn(Flux.fromIterable(profiles));

        try {
            mockMvc.perform(get("/profiles/user/12").header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(profiles)));
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    void getProfilesByUserId_NoProfilesFound() {
        when(profileService.getProfilesByUserId("12")).thenReturn(Flux.empty());

        try {
            mockMvc.perform(get("/profiles/user/12").header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk()) // Assuming an empty list returns OK status
                    .andExpect(content().json("[]"));
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    void getProfileById_UserHasConsentButPermissionFalse() {
        Profile profile = new Profile();
        profile.setProfileId("3959");
        profile.setUserId("otherUserId"); // Different user ID

        Consent consent = new Consent("consentId", "12", "3959", false, new Date()); // Consent is false

        when(profileService.getProfileById("3959")).thenReturn(Mono.just(profile));
        when(consentService.getConsentByProfileId("3959")).thenReturn(Mono.just(consent));

        try {
            mockMvc.perform(get("/profiles/3959").header("Authorization", "Bearer " + token))
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    void updateProfile_ProfileExistsButUserIdMismatch() {
        Profile profile = new Profile();
        profile.setUserId("otherUserId"); // Different user ID
        profile.setProfileId("3959");

        when(profileService.getProfileById("3959")).thenReturn(Mono.just(profile));

        try {
            mockMvc.perform(put("/profiles/3959")
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(profile)))
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    void getProfileById_ProfileFoundButNoConsentRecord() {
        Profile profile = new Profile();
        profile.setProfileId("3959");
        profile.setUserId("otherUserId"); // Different user ID

        when(profileService.getProfileById("3959")).thenReturn(Mono.just(profile));
        when(consentService.getConsentByProfileId("3959")).thenReturn(Mono.justOrEmpty(null)); // No consent record

        try {
            mockMvc.perform(get("/profiles/3959").header("Authorization", "Bearer " + token))
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    void updateProfile_ProfileExistsButUserIdMismatch_DifferentScenario() {
        Profile existingProfile = new Profile();
        existingProfile.setUserId("otherUserId"); // Different user ID
        existingProfile.setProfileId("3959");

        Profile updateProfile = new Profile(); // The profile details to be updated
        updateProfile.setUserId("12"); // User ID of the logged-in user

        when(profileService.getProfileById("3959")).thenReturn(Mono.just(existingProfile));

        try {
            mockMvc.perform(put("/profiles/3959")
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateProfile)))
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }


}

package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.security.UserPrincipal;
import com.advswesome.advswesome.repository.document.Consent;
import com.advswesome.advswesome.service.ConsentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.advswesome.advswesome.repository.document.Profile;
import com.advswesome.advswesome.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    private final ProfileService profileService;
    private final ConsentService consentService;

    @Autowired
    public ProfileController(ProfileService profileService, ConsentService consentService) {
        this.profileService = profileService;
        this.consentService = consentService;
    }

    @PostMapping
    public ResponseEntity<Profile> createProfile(
        @AuthenticationPrincipal UserPrincipal principal,
        @RequestBody Profile profile)
    {
        if (profile.getUserId().equals(principal.getUserId())) {
            Mono<Profile> profileMono = profileService.createProfile(profile);
            Profile newProfile  = profileMono.block();
            return ResponseEntity.status(HttpStatus.OK).body(newProfile);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }

    @GetMapping("/{profileId}")
    public ResponseEntity<Profile> getProfileById(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable String profileId)
    {
        Mono<Consent> consentMono = consentService.getConsentByProfileId(profileId);
        Consent foundConsent = consentMono.block();

        Mono<Profile> profileMono = profileService.getProfileById(profileId);
        Profile foundProfile  = profileMono.block();
        //return ResponseEntity.status(HttpStatus.OK).body(foundProfile);

        if (foundProfile != null) {
            // Check if the current user is the profile owner or has consent
            boolean isOwner = foundProfile.getUserId().equals(principal.getUserId());
            boolean hasConsent = foundConsent != null && foundConsent.isPermission() && foundConsent.getUserId().equals(principal.getUserId());

            if (isOwner || hasConsent) {
                return ResponseEntity.ok(foundProfile);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{profileId}")
    public ResponseEntity<Profile> updateProfile(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable String profileId,
        @RequestBody Profile profile)
    {
        // TODO: updated the dates??
        return profileService.getProfileById(profileId)
                .flatMap(existingProfile -> {
                    if (existingProfile != null && existingProfile.getUserId().equals(principal.getUserId())) {
                        profile.setProfileId(profileId);
                        return profileService.updateProfile(profile);
                    } else {
                        return Mono.error(new AccessDeniedException("Access denied"));
                    }
                })
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.status(HttpStatus.FORBIDDEN).build())
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .block();
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteProfile(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable String profileId
    ) {
        Profile existingProfile = profileService.getProfileById(profileId).block();

        if (existingProfile != null && existingProfile.getUserId().equals(principal.getUserId())) {
            profileService.deleteProfile(profileId).block(); // Blocking call
            return ResponseEntity.noContent().build();
        } else if (existingProfile == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Profile>> getProfilesByUserId(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable String userId)
    {
        Flux<Profile> profilesFlux = profileService.getProfilesByUserId(userId);
        List<Profile> profilesList = profilesFlux.collectList().block();
        return ResponseEntity.status(HttpStatus.OK).body(profilesList);
    }

}

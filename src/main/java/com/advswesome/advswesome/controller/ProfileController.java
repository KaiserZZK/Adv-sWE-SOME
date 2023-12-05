package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.security.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.advswesome.advswesome.repository.document.Profile;
import com.advswesome.advswesome.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<Profile> createProfile(
        @AuthenticationPrincipal UserPrincipal principal,
        @RequestBody Profile profile)
    {
        Mono<Profile> profileMono = profileService.createProfile(profile);
        Profile newProfile  = profileMono.block();
        return ResponseEntity.status(HttpStatus.OK).body(newProfile);
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<Profile> getProfileById(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable String profileId)
    {
        Mono<Profile> profileMono = profileService.getProfileById(profileId);
        Profile foundProfile  = profileMono.block();
        return ResponseEntity.status(HttpStatus.OK).body(foundProfile);
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<Profile> updateProfile(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable String profileId,
        @RequestBody Profile profile)
    {
        // TODO: updated the dates??
        return profileService.getProfileById(profileId)
                .flatMap(existing -> {
                    profile.setProfileId(profileId);
                    return profileService.updateProfile(profile);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .block();
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteProfile(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable String profileId
    ) {
        Mono<Void> profileMono = profileService.deleteProfile(profileId);
        var deletedProfile = profileMono.block();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedProfile);
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

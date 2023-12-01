package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.security.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.advswesome.advswesome.repository.document.Profile;
import com.advswesome.advswesome.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public Mono<Profile> createProfile(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Profile profile) {
        return profileService.createProfile(profile);
    }

    @GetMapping("/{profileId}")
    public Mono<Profile> getProfileById(@PathVariable String profileId) {
        return profileService.getProfileById(profileId);
    }

    @PutMapping("/{profileId}")
    public Mono<ResponseEntity<Profile>> updateProfile(@PathVariable String profileId, @RequestBody Profile profile) {
        return profileService.getProfileById(profileId)
                .flatMap(existing -> {
                    profile.setProfileId(profileId);
                    return profileService.updateProfile(profile);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{profileId}")
    public Mono<ResponseEntity<Void>> deleteProfile(@PathVariable String profileId) {
        return profileService.getProfileById(profileId)
                .flatMap(existing -> profileService.deleteProfile(profileId).thenReturn(existing))
                .map(profile -> ResponseEntity.noContent().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public Flux<Profile> getProfilesByUserId(@PathVariable String userId) {
        return profileService.getProfilesByUserId(userId);
    }
}

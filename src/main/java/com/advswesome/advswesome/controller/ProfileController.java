package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Profile;
import com.advswesome.advswesome.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public Mono<Profile> createProfile(@RequestBody Profile profile) {
        return profileService.createProfile(profile);
    }

    @GetMapping("/{profileId}")
    public Mono<Profile> getProfileById(@PathVariable String profileId) {
        return profileService.getProfileById(profileId);
    }

    @PutMapping("/{profileId}")
    public Mono<Profile> updateProfile(@PathVariable String profileId, @RequestBody Profile profile) {
        // Assuming profileId in the path is used to ensure you update the correct profile
        profile.setProfileId(profileId);
        return profileService.updateProfile(profile);
    }

    @DeleteMapping("/{profileId}")
    public Mono<Void> deleteProfile(@PathVariable String profileId) {
        return profileService.deleteProfile(profileId);
    }
}

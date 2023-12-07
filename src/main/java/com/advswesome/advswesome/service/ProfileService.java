package com.advswesome.advswesome.service;

import com.advswesome.advswesome.repository.ProfileRepository;
import com.advswesome.advswesome.repository.document.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Mono<Profile> createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public Mono<Profile> getProfileById(String profileId) {
        return profileRepository.findById(profileId);
    }

    public Mono<Profile> updateProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public Mono<Void> deleteProfile(String profileId) {
        return profileRepository.deleteById(profileId);
    }

    public Flux<Profile> getProfilesByUserId(String userId) {
        return profileRepository.findByUserId(userId);
    }

}

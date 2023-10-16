package com.advswesome.advswesome.repository.document;


import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;

import java.util.List;

@Document(collectionName = "profiles")
public class Profile {

    @DocumentId
    private String profileId;

    private int age;
    private String sex;
    private String location;
    private String physicalFitness;
    private String languagePreference;
    private List<MedicalHistory> medicalHistory;

    // Constructors
    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }


    public static class MedicalHistory {
        private String diseaseName;
        private String diagnosedAt;
        private String treatment;

        // Constructors
    }
}

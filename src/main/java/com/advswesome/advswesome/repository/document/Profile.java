package com.advswesome.advswesome.repository.document;


import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;

import java.util.List;

@Document(collectionName = "profiles")
public class Profile {

    @DocumentId
    private String profileId;
    private String userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhysicalFitness() {
        return physicalFitness;
    }

    public void setPhysicalFitness(String physicalFitness) {
        this.physicalFitness = physicalFitness;
    }

    public String getLanguagePreference() {
        return languagePreference;
    }

    public void setLanguagePreference() {
        this.languagePreference = languagePreference;
    }

    public List<MedicalHistory> getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(List<MedicalHistory> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public static class MedicalHistory {
        private String diseaseName;
        private String diagnosedAt;
        private String treatment;

        // Constructors

        public MedicalHistory() {
            // Default constructor for deserialization
        }

        public MedicalHistory(String diseaseName, String diagnosedAt, String treatment) {
            this.diseaseName = diseaseName;
            this.diagnosedAt = diagnosedAt;
            this.treatment = treatment;
        }

        public String getDiseaseName() {
            return diseaseName;
        }

        public void setDiseaseName(String diseaseName) {
            this.diseaseName = diseaseName;
        }

        public String getDiagnosedAt() {
            return diagnosedAt;
        }

        public void setDiagnosedAt(String diagnosedAt) {
            this.diagnosedAt = diagnosedAt;
        }

        public String getTreatment() {
            return treatment;
        }

        public void setTreatment(String treatment) {
            this.treatment = treatment;
        }

    }

}

package com.advswesome.advswesome.repository.document;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;

import java.util.Date;

@Document(collectionName = "consents")
public class Consent {

    @DocumentId
    private String consentId;

    // TODO: add profile id later
    private String profileId;
    private boolean permission;
    private Date updatedAt;

    public Consent() {
    }

    public Consent(String consentId, String profileId, boolean permission, Date updatedAt) {
        this.consentId = consentId;
        this.profileId = profileId;
        this.permission = permission;
        this.updatedAt = updatedAt;
    }

    public String getConsentId() {
        return consentId;
    }

    public void setConsentId(String consentId) {
        this.consentId = consentId;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }
}

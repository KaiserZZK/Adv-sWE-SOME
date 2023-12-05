package com.advswesome.advswesome.repository.document;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;

import java.util.Objects;

@Document(collectionName = "prescriptions")
public class Prescription {

    @DocumentId
    private String prescriptionId;
    private String profileId;
    private String rx_number;
    private String rx_provider;
    private String rx_name;
    private int refills;
    private int quantity;

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getRxNumber() {
        return rx_number;
    }

    public void setRxNumber(String rx_number) {
        this.rx_number = rx_number;
    }

    public String getRxProvider() {
        return rx_provider;
    }

    public void setRxProvider(String rx_provider) {
        this.rx_provider = rx_provider;
    }

    public String getRxName() {
        return rx_name;
    }

    public void setRxName(String rx_name) {
        this.rx_name = rx_name;
    }

    public int getRefills() {
        return refills;
    }

    public void setRefills(int refills) {
        this.refills = refills;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profile_id) {
        this.profileId = profile_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescription that = (Prescription) o;
        return refills == that.refills && quantity == that.quantity && Objects.equals(prescriptionId, that.prescriptionId) && Objects.equals(profileId, that.profileId) && Objects.equals(rx_number, that.rx_number) && Objects.equals(rx_provider, that.rx_provider) && Objects.equals(rx_name, that.rx_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prescriptionId, profileId, rx_number, rx_provider, rx_name, refills, quantity);
    }
}

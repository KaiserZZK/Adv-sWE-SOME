package com.advswesome.advswesome.repository.document;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;

@Document(collectionName = "prescription")
public class Prescription {

    @DocumentId
    private String prescriptionId;

    // TODO: add profile id later
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

    public String getRx_number() {
        return rx_number;
    }

    public void setRx_number(String rx_number) {
        this.rx_number = rx_number;
    }

    public String getRx_provider() {
        return rx_provider;
    }

    public void setRx_provider(String rx_provider) {
        this.rx_provider = rx_provider;
    }

    public String getRx_name() {
        return rx_name;
    }

    public void setRx_name(String rx_name) {
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




}

package com.advswesome.advswesome.repository.document;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;

import java.util.UUID;

@Document(collectionName = "clients")
public class Client {
    @DocumentId
    private String clientId;

    private String apiKey;

    private String appName;

    private String clientType;

    public Client() {
        this.apiKey = generateUniqueApiKey();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    private String generateUniqueApiKey() {
        return UUID.randomUUID().toString();
    }
}

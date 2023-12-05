# Adv-sWE-SOME
Welcome to Adv-sWE-SOME, our incredible project crafted during the Fall 23 COMS W4156 Advanced Software Engineering course. We’ve leveraged the power of Spring Boot to engineer an efficient API service, and paired it with Google Cloud Firestore for a seamless, robust database experience—all readily configured for local development and testing.

## Third Party Library Used
Below are third party libraries included in build files.

1. *[Maven](https://maven.apache.org)*: Dependency management
2. *[Spring Boot](https://spring.io/projects/spring-boot/)*: Project Framework, support creation of web RESTful APIs.
3. *[Google Cloud Firestore Database](https://cloud.google.com/firestore#)*: Project database on cloud.
4. *[Spring Cloud GCP](https://spring.io/projects/spring-cloud-gcp)*: Access and connect to GCP databse.
5. *[Junit 5](https://junit.org/junit5/)*: Unit Testing.
6. *[Mockito](https://site.mockito.org)*: Service mocking Library for Unit Testing.
7. *[Reactor Test](https://projectreactor.io/docs/test/release/api/)*: Aids in effectively testing reactive programming constructs.


## Building and Running the Project

### GCP credential Setup
Download GCP SDK - MacOS
```
brew install --cask google-cloud-sdk   
```

Initiate and Login to GCP Console and Select Project Adv-sWE-SOME
(group-member and teaching staff team are granted access. Login using your lion mail)

access member:
- yq2339@columbia.edu (Team Member)
- fz2356@columbia.edu (Team Member)
- yw3959@columbia.edu (Team Member)
- zz2919@columbia.edu (Team Member)
- zz2978@columbia.edu (Team Member)
- rs4489@columbia.edu (TA)

Please contact yq2339@columbia.edu for access grant.

```
gcloud init

gcloud auth application-default login  
```

### To build project:
```
mvn clean install
```

or 

```
./mvnw clean install
```

### Run Project: 

```
mvn spring-boot:run
```

or

```
 ./mvnw spring-boot:run
```

### Run All Unit Tests:
```
mvn test
```

or 

```
./mvnw test
```

## CheckStyler
Implemented with the Maven Checkstyle Plugin to ensure code quality. Configurations and rules are specified in the checkstyle.xml file.
Run check style using:
```
mvn checkstyle:check
```

## Check Styler Report:

[Link](https://docs.google.com/document/d/1p9d6-la6pbGDijPt1cAh0kLuJD1Z9VA-zEDq7zqw6z0/edit?usp=sharing) to Checkstyler report.

## API System Testing Report:

[Link](https://docs.google.com/document/d/1jxLKYlbAJtigIxv0DPgtTj_rBpCIkzH_BqBnStyGRUs/edit?usp=sharing) to API system testing report.

## API documentations

### Client Registration

- #### /clients/register
  - `POST`
  - Description: Register a new app/client to use our service
  - Input:
    - Header: 
      - `Content-Type`: `application/json`
    - Body(JSON) Fields:
      - `appName` (String)
      - `clientType` ("INDIVIDUAL", "ORGANIZATION", "HOSPITAL").
  - Output:
    - On Success: 
      - Status Code: `200`
      - JSON with field
        - `clientId`
        - `apiKey`
        - `appName`
        - `clientType`

### Authentication

- #### users/auth/register
  - `POST`
  - Description: Register a new user
  - Input:
    - Header:
      - `Content-Type`: `application/json`
    - Body(JSON) Fields:
      - `clientId` (String)
      - `username` (String)
      - `password` (String)
      - `email` (String)
      - `createdAt` (Timestamp String)
      - `updatedAt` (Timestamp String)
  - Output:
    - On Success:
      - Status Code: `200`
      - JSON with field
        - `userId`
        - `clientId`
        - `username` 
        - `password`
        - `email`
        - `createdAt`
        - `updatedAt`

- #### users/auth/login
  - `POST`
  - Description: Login a user
  - Input:
    - Header
      - `Content-Type`: `application/json`
    - Body(JSON) Fields:
      - `username` (String)
      - `password` (String)
  - Output
    - On Success:
      - Status Code: `200`
      - JSON with JWT token

### Profile

- #### /profiles
  - `POST`
  - Description:  Create a new profile
  - Authentication: Required
  - Input: 
    - Header
      - `Content-Type`: `application/json`
    - Body(JSON) Fields:
      - `userId` (String)
      - `age` (String)
      - `sex` (String)
      - `location` (String)
      - `physical_fitness` (String)
      - `language_preference` (String)
      - `medical_history`  (List of medical history)
        - medical history:
          ```
          {
          `disease_name`,
          `diagnosed_at`, 
          `treatment`
          }
          ```
  - Output:
    - On Success:
      - Status Code: `200`
      - JSON with Fields:
        - `profileId`
        - `userId`
        - `age` 
        - `sex` 
        - `location`
        - `physical_fitness` 
        - `language_preference` 
        - `medical_history` 


- #### /profiles/{profileId}
  - `GET`
  - Description: Get a profile by ID
  - Authentication: Required, owner only
  - Output:
    - On Success:
      - Status Code: `200`
      - JSON with Fields:
          - `profileId`
          - `userId`
          - `age`
          - `sex`
          - `location`
          - `physical_fitness`
          - `language_preference`
          - `medical_history` 

- #### /profiles/{profileId}
  - `PUT`
  - Description: Update a profile
  - Authentication: Required, owner only
  - Input
    - Header
        - `Content-Type`: `application/json`
    - Body(JSON) Fields:
        - `userId` (String)
        - `age` (String)
        - `sex` (String)
        - `location` (String)
        - `physical_fitness` (String)
        - `language_preference` (String)
        - `medical_history`  (List of medical history)
          - medical history:
            ```
            {
            `disease_name`,
            `diagnosed_at`, 
            `treatment`
            }
            ```
  - Output:
    - On Success:
      - Status Code: `200`
      - JSON with Fields:
          - `profileId`
          - `userId`
          - `age`
          - `sex`
          - `location`
          - `physical_fitness`
          - `language_preference`
          - `medical_history` 

- #### /profiles/user/{userId}
  - `GET`
  - Description: Get all profile of this user
  - Authentication: Required, owner only
  - Output:
    - On Success:
      - Status Code: `200`
      - JSON with list of profiles

- #### /profiles/{profileId}
  - `DELETE`
  - Description: Delete a profile
  - Authentication: Required, owner only
  - Output:
    - On Success:
      - Status Code: `200`

### Prescriptions

- #### /prescriptions
  - `POST`
  - Description: Create a new prescription
  - Authentication: Required
  - Input
    - Header
      - `Content-Type`: `application/json`
    - Body
      - `prescriptionId` (String)
      - `profileId` (String)
      - `rx_number` (Integer)
      - `rx_provider` (String)
      - `rx_name` (String)
      - `refills` (Integer)
      - `quantity` (Integer)
  - Output 
    - On Success
      - Status Code: `201`
      - JSON with field:
        - `prescriptionId` (String)
        - `profileId` (String)
        - `rx_number` (Integer)
        - `rx_provider` (String)
        - `rx_name` (String)
        - `refills` (Integer)
        - `quantity` (Integer)

- #### /prescription/{prescriptionId}
  - `GET`
  - Description: Get a prescription by Id 
  - Authentication: Required, owner only
  - Output:
    - On Success
      - Status Code: `200`
      - JSON with Fields:
        - `prescriptionId`
        - `profileId` 
        - `rx_number`
        - `rx_provider`
        - `rx_name` 
        - `refills` 
        - `quantity`
    - On Error - Not Found
      - Status Code: `404`

- #### /prescription/{prescriptionId}
  - `PUT`
  - Description: Update a prescription
  - Authentication: Required, owner only
  - Input
    - Header
        - `Content-Type`: `application/json`
    - Body
        - `prescriptionId` (String)
        - `profileId` (String)
        - `rx_number` (Integer)
        - `rx_provider` (String)
        - `rx_name` (String)
        - `refills` (Integer)
        - `quantity` (Integer)
  - Output
    - On Success
      - Status Code: `200`
      - JSON with Fields:
          - `prescriptionId`
          - `profileId`
          - `rx_number`
          - `rx_provider`
          - `rx_name`
          - `refills`
          - `quantity`
    - On Error - Not Found
        - Status Code: `404`

- #### /prescription/{prescriptionId}
  - `DELETE`
  - Description: Delete a prescription 
  - Authentication: Required, owner only
  - Output:
    - On Success
      - Status Code: `204`
    - On Error - Not Found 
      - Status Code: `404`

### Consents 

- #### /consent
  - `POST`
  - Description: Create a new consent 
  - Authentication: Required
  - Input:
    - Header
        - `Content-Type`: `application/json`
    - Body
      - `consentId` (String)
      - `userId` (String)
      - `profileId` (String)
      - `permission` (Boolean)
      - `updatedAt` (Timestamp String)
  - Output:
    - On Success
      - Status Code: `201`
      - Message: `Consent created successfully with ID {consentID}`
    - On Duplicated `consentId`:
      - Status Code: `409`
      - Message: `Consent with ID {consentId} already exists`

- #### /consent/{consentId}
  - `GET`
  - Description: Get consent by consentId 
  - Authentication: Required
  - Output:
    - On Success
      - Status Code: `200`
      - JSON with Fields:
          - `consentId`
          - `userId`
          - `profileId`
          - `permission`
          - `updatedAt` 
    - On Error - Not Found
      - Status Code: `404`

- #### /consent/{consentId}
  - `PUT`
  - Description: Update a consent
  - Authentication: Required
  - Input:
    - Header
        - `Content-Type`: `application/json`
    - Body
        - `consentId` (String)
        - `userId` (String)
        - `profileId` (String)
        - `permission` (Boolean)
        - `updatedAt` (Timestamp String)
  - Output:
    - On Success
      - Status Code: `200`
      - JSON with Fields:
          - `consentId`
          - `userId`
          - `profileId`
          - `permission`
          - `updatedAt` 
    - On Error - Not Found
      - Status Code: `404`

- #### /consent/{consentId}
  - `DELETE` 
  - Description: Delete a consent
  - Authentication: Required
  - Output:
    - On Success
      - Status Code: `200`
    - On Error - Not Found
      - Status Code: `404`

### Analytics

- #### /analytics/profiles
  - `GET`
  - Description: Get profiles based on filters
  - Authentication: Not required data is anonymized
  - Input: 
    -  Header
        - `Content-Type`: `application/json`
    - Body
      - `age` (Optional String)
      - `medical_history` (Optional medical history)
      - `location` (Optional String)
  - Output:
    - On Success
      - Status Code: `200`
      - JSON - List of Profiles without profile IDs

- #### /prescription/profile/{profileId}
  - `GET`
  - Description: Get all prescription under the given ProfileId
  - Authentication: Required
  - Output:
    - On Success
      - Status Code: `200`
      - JSON - List of prescriptions
    - On Error - Not Found
        - Status Code: `404`

- #### /consent/profile/{profileId}
  - `GET`
  - Description: Get the consent info under the given ProfileId
  - Authentication: Required
  - Output:
    - On Success
      - Status Code: `200`
      - JSON - Consent info

- #### /consent/user/{userId}
  - `GET`
  - Description: Get the consent info under the given UserId
  - Authentication: Required
  - Output:
    - On Success
      - Status Code: `200`
      - JSON - Consent info


    



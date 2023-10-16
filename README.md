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


## CheckStyler
Implemented with the Maven Checkstyle Plugin to ensure code quality. Configurations and rules are specified in the checkstyle.xml file. 
Run check style using:
```
mvn checkstyle:check
```

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

## API documentations




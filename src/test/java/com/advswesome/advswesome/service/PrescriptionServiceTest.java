package com.advswesome.advswesome.service;

import com.advswesome.advswesome.repository.PrescriptionRepository;
import com.advswesome.advswesome.repository.document.Prescription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// test for prescription services
public class PrescriptionServiceTest {
    @InjectMocks
    private PrescriptionService prescriptionService;

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRx() {
        Prescription p = new Prescription();
        p.setRxNumber("12345");
        p.setProfileId("pp");
        p.setRxProvider("ProviderA");
        p.setRxName("MedicineA");
        p.setRefills(2);
        p.setQuantity(30);

        // when new prescription added to the repository, use Mockito to return a Mono of Prescription
        when(prescriptionRepository.save(p)).thenReturn(Mono.just(p));
        Prescription savedPrescription = prescriptionService.createPrescription(p).block();
        // testing if the new prescription object exists and the value match
        assertNotNull(savedPrescription);
        assertEquals(p.getRxNumber(), savedPrescription.getRxNumber());

        verify(prescriptionRepository, times(1)).save(p);
    }

    @Test
    void testGetRxByID(){
        Prescription p = new Prescription();
        p.setRxNumber("12345");
        p.setProfileId("pp");
        p.setRxProvider("ProviderA");
        p.setRxName("MedicineA");
        p.setRefills(2);
        p.setQuantity(30);

        // when findById method is called, use Mockito to return a Mono of Prescription
        when(prescriptionRepository.findById("12345")).thenReturn(Mono.just(p));
        Prescription fetchedP = prescriptionService.getPrescriptionById("12345").block();

        // test if getting the correct prescription with given id
        assertNotNull(fetchedP);
        assertEquals(fetchedP.getRxNumber(), p.getRxNumber());

    }


    @Test
    void testUpdate(){
        Prescription p = new Prescription();
        p.setRxNumber("12345");
        p.setProfileId("pp");
        p.setRxProvider("ProviderA");
        p.setRxName("MedicineA");
        p.setRefills(2);
        p.setQuantity(30);

        // when findById method is called, use Mockito to return a Mono of Prescription
        when(prescriptionRepository.findById("12345")).thenReturn(Mono.just(p));
        when(prescriptionRepository.save(any(Prescription.class))).thenReturn(Mono.just(p));

        p.setQuantity(10);
        Prescription fetchedP = prescriptionService.updatePrescription(p).block();
        // test if the update has effects on the given prescription
        assertNotNull(fetchedP);
        assertEquals(fetchedP.getQuantity(), 10);

    }

    @Test
    void testDeleteRx() {
        Prescription p = new Prescription();
        p.setRxNumber("12345");
        p.setProfileId("pp");
        p.setRxProvider("ProviderA");
        p.setRxName("MedicineA");
        p.setRefills(2);
        p.setQuantity(10);

        // when findById method is called, use Mockito to return a Mono of Prescription
        when(prescriptionRepository.findById("12345")).thenReturn(Mono.just(p));
        when(prescriptionRepository.deleteById("12345")).thenReturn(Mono.empty());

        prescriptionService.deletePrescription("12345").block();
        // make sure deletion has effect and only be called once
        verify(prescriptionRepository, times(1)).deleteById("12345");
    }

    @Test
    void testGetMultiplePrescriptionsByProfileId() {
        String profileId = "testProfileId";

        Prescription prescription1 = new Prescription();
        prescription1.setPrescriptionId("rx1");
        prescription1.setProfileId(profileId);

        Prescription prescription2 = new Prescription();
        prescription2.setPrescriptionId("rx2");
        prescription2.setProfileId(profileId);

        // when findByProfileId method is called, use Mockito to return a flux of Prescriptions
        when(prescriptionRepository.findByProfileId(profileId)).thenReturn(Flux.just(prescription1, prescription2));

        Flux<Prescription> prescriptions = prescriptionService.getPrescriptionsByProfileId(profileId);

        // verify each item in the returning prescriptions
        StepVerifier.create(prescriptions)
                .expectNext(prescription1)
                .expectNext(prescription2)
                .verifyComplete();

        verify(prescriptionRepository, times(1)).findByProfileId(profileId);
    }

}




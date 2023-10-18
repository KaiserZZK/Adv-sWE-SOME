package com.advswesome.advswesome.service;

import com.advswesome.advswesome.repository.PrescriptionRepository;
import com.advswesome.advswesome.repository.document.Prescription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

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
        p.setRx_number("12345");
        p.setProfileId("pp");
        p.setRx_provider("ProviderA");
        p.setRx_name("MedicineA");
        p.setRefills(2);
        p.setQuantity(30);

        when(prescriptionRepository.save(p)).thenReturn(Mono.just(p));
        Prescription savedPrescription = prescriptionService.createPrescription(p).block();
        // testing if the new prescription object exists and the value match
        assertNotNull(savedPrescription);
        assertEquals(p.getRx_number(), savedPrescription.getRx_number());

        verify(prescriptionRepository, times(1)).save(p);
    }

    @Test
    void testGetRxByID(){
        Prescription p = new Prescription();
        p.setRx_number("12345");
        p.setProfileId("pp");
        p.setRx_provider("ProviderA");
        p.setRx_name("MedicineA");
        p.setRefills(2);
        p.setQuantity(30);

        when(prescriptionRepository.findById("12345")).thenReturn(Mono.just(p));
        Prescription fetchedP = prescriptionService.getPrescriptionById("12345").block();

        // test if getting the correct prescription with given id
        assertNotNull(fetchedP);
        assertEquals(fetchedP.getRx_number(), p.getRx_number());

    }


    @Test
    void testUpdate(){
        Prescription p = new Prescription();
        p.setRx_number("12345");
        p.setProfileId("pp");
        p.setRx_provider("ProviderA");
        p.setRx_name("MedicineA");
        p.setRefills(2);
        p.setQuantity(30);

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
        p.setRx_number("12345");
        p.setProfileId("pp");
        p.setRx_provider("ProviderA");
        p.setRx_name("MedicineA");
        p.setRefills(2);
        p.setQuantity(10);

        when(prescriptionRepository.findById("12345")).thenReturn(Mono.just(p));
        when(prescriptionRepository.deleteById("12345")).thenReturn(Mono.empty());

        prescriptionService.deletePrescription("12345").block();
        // make sure deletion has effect and only be called once
        verify(prescriptionRepository, times(1)).deleteById("12345");
    }

}




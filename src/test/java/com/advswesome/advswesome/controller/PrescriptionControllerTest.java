package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.document.Prescription;
import com.advswesome.advswesome.service.PrescriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.*;

public class PrescriptionControllerTest {


    @InjectMocks
    private PrescriptionController prescriptionController;

    @Mock
    private PrescriptionService prescriptionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatRx() throws Exception {
        Prescription p = new Prescription();
        p.setRx_number("12345");
        p.setRx_provider("ProviderA");
        p.setRx_name("MedicineA");
        p.setRefills(2);
        p.setQuantity(30);

        when(prescriptionService.createPrescription(p)).thenReturn(Mono.just(p));

        prescriptionController.createPrescription(p).block();

        verify(prescriptionService, times(1)).createPrescription(p);

    }


    @Test
    void testGetbyRxId(){
        Prescription p = new Prescription();
        p.setRx_number("12345");
        p.setProfile_id("pp");
        p.setRx_provider("ProviderA");
        p.setRx_name("MedicineA");
        p.setRefills(2);
        p.setQuantity(30);

        when(prescriptionService.getPrescriptionById("12345")).thenReturn(Mono.just(p));
        prescriptionController.getPrescriptionById("12345").block();
        verify(prescriptionService, times(1)).getPrescriptionById("12345");

    }


    @Test
    void testUpdatebyRxId(){
        Prescription p = new Prescription();
        p.setRx_number("12345");
        p.setProfile_id("pp");
        p.setRx_provider("ProviderA");
        p.setRx_name("MedicineA");
        p.setRefills(2);
        p.setQuantity(30);

        when(prescriptionService.getPrescriptionById("12345")).thenReturn(Mono.just(p));
        when(prescriptionService.updatePrescription(p)).thenReturn(Mono.just(p));

        prescriptionController.updatePrescription("12345",p).block();
        verify(prescriptionService, times(1)).getPrescriptionById("12345");

    }

    @Test
    void testDeleteRx() {
        Prescription p = new Prescription();
        p.setRx_number("12345");
        p.setProfile_id("pp");
        p.setRx_provider("ProviderA");
        p.setRx_name("MedicineA");
        p.setRefills(2);
        p.setQuantity(10);

        when(prescriptionService.getPrescriptionById("12345")).thenReturn(Mono.just(p));
        when(prescriptionService.deletePrescription("12345")).thenReturn(Mono.empty());

        prescriptionController.deletePrescription("12345").block();


        verify(prescriptionService, times(1)).deletePrescription("12345");
    }
}

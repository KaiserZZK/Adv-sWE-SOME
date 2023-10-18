package com.advswesome.advswesome.controller;

import com.advswesome.advswesome.repository.PrescriptionRepository;
import com.advswesome.advswesome.repository.document.Prescription;
import com.advswesome.advswesome.service.PrescriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import reactor.core.publisher.Mono;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class PrescriptionControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    @InjectMocks
    private PrescriptionController prescriptionController;

    @Mock
    private PrescriptionService prescriptionService;

    @BeforeEach
    void setup() {
//        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();;
    }

//    @Test
//    void testCreatRx() throws Exception {
//        Prescription p = new Prescription();
//        p.setRx_number("12345");
//        p.setRx_provider("ProviderA");
//        p.setRx_name("MedicineA");
//        p.setRefills(2);
//        p.setQuantity(30);
//
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(p);
//
//
//        when(prescriptionService.createPrescription(p)).thenReturn(Mono.just(p));
//
//        mockMvc.perform(post("/prescriptions")
//                .contentType("application/json")
//                .content(json))
//                .andExpect(status().isOk())
//                        .andDo(print());
////                .andExpect(jsonPath("$.rx_number").value("12345"));
//
////        System.out.println(result.getResponse().getContentAsString());
//
//        verify(prescriptionService, times(1)).createPrescription(any(Prescription.class));
//
//    }
}

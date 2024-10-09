package com.crimsonlogic.freelancersystem.controller;

import com.crimsonlogic.freelancersystem.dto.ServiceRequestDTO;
import com.crimsonlogic.freelancersystem.entity.ServiceRequest;
import com.crimsonlogic.freelancersystem.entity.User;
import com.crimsonlogic.freelancersystem.service.ServiceRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServiceRequestController.class)
public class ServiceRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceRequestService serviceRequestService;

    @Autowired
    private ObjectMapper objectMapper;

    private ServiceRequestDTO serviceRequestDTO;
    private ServiceRequest serviceRequest;
    private User client;
    private User freelancer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        serviceRequestDTO = new ServiceRequestDTO();
        serviceRequestDTO.setServiceDescription("Need a website design");
        serviceRequestDTO.setPrice(500.0);

        client = new User();
        client.setId("C1");
        client.setUsername("clientUser");
        client.setPassword("password123");

        freelancer = new User();
        freelancer.setId("F1");
        freelancer.setUsername("freelancerUser");
        freelancer.setPassword("password123");

        serviceRequest = new ServiceRequest();
        serviceRequest.setId("SR1");
        serviceRequest.setServiceDescription("Need a website design");
        serviceRequest.setPrice(500.0);
        serviceRequest.setStatus("pending");
        serviceRequest.setClient(client);
        serviceRequest.setFreelancer(freelancer);
     
    }

    @Test
    public void testCreateServiceRequest() throws Exception {
        when(serviceRequestService.createServiceRequest(any(ServiceRequestDTO.class), anyString(), anyString())).thenReturn(serviceRequest);

        mockMvc.perform(post("/api/service-requests/create/U1/C1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(serviceRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceDescription").value("Need a website design"))
                .andExpect(jsonPath("$.price").value(500.0));
    }

    @Test
    public void testGetServiceRequestById() throws Exception {
        when(serviceRequestService.getServiceRequestById(anyString())).thenReturn(serviceRequestDTO);

        mockMvc.perform(get("/api/service-requests/SR1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceDescription").value("Need a website design"))
                .andExpect(jsonPath("$.price").value(500.0));
    }

    @Test
    public void testUpdateServiceRequest() throws Exception {
        when(serviceRequestService.updateServiceRequest(anyString(), any(ServiceRequestDTO.class))).thenReturn(serviceRequestDTO);

        mockMvc.perform(put("/api/service-requests/SR1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(serviceRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceDescription").value("Need a website design"))
                .andExpect(jsonPath("$.price").value(500.0));
    }

    @Test
    public void testDeleteServiceRequest() throws Exception {
        doNothing().when(serviceRequestService).deleteServiceRequest(anyString());

        mockMvc.perform(delete("/api/service-requests/SR1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllServiceRequests() throws Exception {
        when(serviceRequestService.getAllServiceRequests()).thenReturn(Arrays.asList(serviceRequest));

        mockMvc.perform(get("/api/service-requests/list"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllServiceRequestsByUserID() throws Exception {
        when(serviceRequestService.getAllServiceRequestsBYUserId(anyString())).thenReturn(Arrays.asList(serviceRequest));

        mockMvc.perform(get("/api/service-requests/list/C1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceDescription").value("Need a website design"))
                .andExpect(jsonPath("$.price").value(500.0));
    }

    @Test
    public void testGetAllServiceRequestsByFreelancerID() throws Exception {
        when(serviceRequestService.getAllServiceRequestsBYFreelancerId(anyString())).thenReturn(Arrays.asList(serviceRequest));

        mockMvc.perform(get("/api/service-requests/listrequest/F1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceDescription").value("Need a website design"))
                .andExpect(jsonPath("$.price").value(500.0));
    }

}

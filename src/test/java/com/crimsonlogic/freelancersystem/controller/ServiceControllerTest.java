package com.crimsonlogic.freelancersystem.controller;

import com.crimsonlogic.freelancersystem.dto.ServiceDTO;
import com.crimsonlogic.freelancersystem.dto.UserDTO;
import com.crimsonlogic.freelancersystem.entity.Service;
import com.crimsonlogic.freelancersystem.service.ServiceService;
import com.crimsonlogic.freelancersystem.service.UserService;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServiceController.class)
public class ServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceService serviceService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private ServiceDTO serviceDTO;
    private Service service;
    private UserDTO userDTO;
    private MockMultipartFile imageFile;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        serviceDTO = new ServiceDTO();
        serviceDTO.setName("Website Design");
        serviceDTO.setDescription("Designing responsive websites");
        serviceDTO.setPrice(500.0);
        serviceDTO.setCategory("SC1");

        service = new Service();
        service.setId("S1");
        service.setName("Website Design");
        service.setDescription("Designing responsive websites");
        service.setPrice(500.0);
        service.setImageUrl("http://localhost:3000/images/sample.jpg");

        userDTO = new UserDTO();
        userDTO.setId("U1");
        userDTO.setUsername("testuser");
        userDTO.setPassword("password123");

        imageFile = new MockMultipartFile("image", "sample.jpg", MediaType.IMAGE_JPEG_VALUE, "sample image content".getBytes());
    }

    @Test
    public void testCreateService() throws Exception {
        when(userService.getUserById(anyString())).thenReturn(userDTO);
        when(serviceService.createService(any(ServiceDTO.class), any(MockMultipartFile.class), any(UserDTO.class))).thenReturn(service);

        MockMultipartFile servicePart = new MockMultipartFile("service", "", "application/json", objectMapper.writeValueAsBytes(serviceDTO));

        mockMvc.perform(multipart("/api/services/create/U1")
                .file(imageFile)
                .file(servicePart)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Website Design"))
                .andExpect(jsonPath("$.description").value("Designing responsive websites"));
    }

    @Test
    public void testGetServiceById() throws Exception {
        when(serviceService.getServiceById(anyString())).thenReturn(serviceDTO);

        mockMvc.perform(get("/api/services/S1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Website Design"))
                .andExpect(jsonPath("$.description").value("Designing responsive websites"));
    }

    @Test
    public void testUpdateService() throws Exception {
        when(serviceService.updateService(anyString(), any(ServiceDTO.class))).thenReturn(serviceDTO);

        mockMvc.perform(put("/api/services/S1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(serviceDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Website Design"))
                .andExpect(jsonPath("$.description").value("Designing responsive websites"));
    }

    @Test
    public void testDeleteService() throws Exception {
        doNothing().when(serviceService).deleteService(anyString());

        mockMvc.perform(delete("/api/services/S1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllServices() throws Exception {
        when(serviceService.getAllServices()).thenReturn(Arrays.asList(service));

        mockMvc.perform(get("/api/services/list"))
                .andExpect(status().isOk());
    }

  
}

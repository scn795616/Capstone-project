package com.crimsonlogic.freelancersystem.controller;

import com.crimsonlogic.freelancersystem.dto.ServiceCategoryDTO;
import com.crimsonlogic.freelancersystem.service.ServiceCategoryService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServiceCategoryController.class)
public class ServiceCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceCategoryService serviceCategoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private ServiceCategoryDTO serviceCategoryDTO;
    private MockMultipartFile imageFile;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        serviceCategoryDTO = new ServiceCategoryDTO();
        serviceCategoryDTO.setId("SC1");
        serviceCategoryDTO.setName("Web Development");
        serviceCategoryDTO.setDescription("Development of websites and web applications");

        imageFile = new MockMultipartFile("image", "sample.jpg", MediaType.IMAGE_JPEG_VALUE, "sample image content".getBytes());
    }

    @Test
    public void testCreateServiceCategory() throws Exception {
        when(serviceCategoryService.createServiceCategory(any(ServiceCategoryDTO.class), any(MockMultipartFile.class))).thenReturn(serviceCategoryDTO);

        MockMultipartFile serviceCategoryPart = new MockMultipartFile("serviceCategory", "", "application/json", objectMapper.writeValueAsBytes(serviceCategoryDTO));

        mockMvc.perform(multipart("/api/service-categories/create")
                .file(imageFile)
                .file(serviceCategoryPart)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Web Development"))
                .andExpect(jsonPath("$.description").value("Development of websites and web applications"));
    }

    @Test
    public void testGetServiceCategoryById() throws Exception {
        when(serviceCategoryService.getServiceCategoryById(anyString())).thenReturn(serviceCategoryDTO);

        mockMvc.perform(get("/api/service-categories/SC1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Web Development"))
                .andExpect(jsonPath("$.description").value("Development of websites and web applications"));
    }

    @Test
    public void testUpdateServiceCategory() throws Exception {
        when(serviceCategoryService.updateServiceCategory(anyString(), any(ServiceCategoryDTO.class), any(MockMultipartFile.class))).thenReturn(serviceCategoryDTO);

        MockMultipartFile serviceCategoryPart = new MockMultipartFile("serviceCategory", "", "application/json", objectMapper.writeValueAsBytes(serviceCategoryDTO));

        mockMvc.perform(multipart("/api/service-categories/SC1")
                .file(imageFile)
                .file(serviceCategoryPart)
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                })
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Web Development"))
                .andExpect(jsonPath("$.description").value("Development of websites and web applications"));
    }

    @Test
    public void testDeleteServiceCategory() throws Exception {
        doNothing().when(serviceCategoryService).deleteServiceCategory(anyString());

        mockMvc.perform(delete("/api/service-categories/SC1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllServiceCategories() throws Exception {
        when(serviceCategoryService.getAllServiceCategories()).thenReturn(Arrays.asList(serviceCategoryDTO));

        mockMvc.perform(get("/api/service-categories/list"))
                .andExpect(status().isOk());
    }
}

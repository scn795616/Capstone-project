package com.crimsonlogic.freelancersystem.service;

import com.crimsonlogic.freelancersystem.dto.ServiceCategoryDTO;
import com.crimsonlogic.freelancersystem.entity.ServiceCategory;
import com.crimsonlogic.freelancersystem.exception.ResourceNotFoundException;
import com.crimsonlogic.freelancersystem.repository.ServiceCategoryRepository;
import com.crimsonlogic.freelancersystem.serviceImpl.ServiceCategoryServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ServiceCategoryServiceImplTest {

    @Mock
    private ServiceCategoryRepository serviceCategoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private ServiceCategoryServiceImpl serviceCategoryService;

    private ServiceCategory serviceCategory;
    private ServiceCategoryDTO serviceCategoryDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        serviceCategory = new ServiceCategory();
        serviceCategory.setId("SC1");
        serviceCategory.setName("Web Development");
        serviceCategory.setDescription("Development of websites and web applications");
        serviceCategory.setImageUrl("http://localhost:3000/images/sample.jpg");

        serviceCategoryDTO = new ServiceCategoryDTO();
        serviceCategoryDTO.setName("Web Development");
        serviceCategoryDTO.setDescription("Development of websites and web applications");
    }

    @Test
    public void testCreateServiceCategory() throws IOException {
        when(modelMapper.map(any(ServiceCategoryDTO.class), eq(ServiceCategory.class))).thenReturn(serviceCategory);
        when(serviceCategoryRepository.save(any(ServiceCategory.class))).thenReturn(serviceCategory);
        when(modelMapper.map(any(ServiceCategory.class), eq(ServiceCategoryDTO.class))).thenReturn(serviceCategoryDTO);
        when(multipartFile.getOriginalFilename()).thenReturn("sample.jpg");
        when(multipartFile.getBytes()).thenReturn(new byte[0]);

        ServiceCategoryDTO createdCategory = serviceCategoryService.createServiceCategory(serviceCategoryDTO, multipartFile);

        assertNotNull(createdCategory);
        assertEquals("Web Development", createdCategory.getName());
        verify(serviceCategoryRepository, times(1)).save(any(ServiceCategory.class));
    }

    @Test
    public void testGetServiceCategoryById() {
        when(serviceCategoryRepository.findById(anyString())).thenReturn(Optional.of(serviceCategory));
        when(modelMapper.map(any(ServiceCategory.class), eq(ServiceCategoryDTO.class))).thenReturn(serviceCategoryDTO);

        ServiceCategoryDTO foundCategory = serviceCategoryService.getServiceCategoryById("SC1");

        assertNotNull(foundCategory);
        assertEquals("Web Development", foundCategory.getName());
        verify(serviceCategoryRepository, times(1)).findById("SC1");
    }

    @Test
    public void testUpdateServiceCategory() throws IOException {
        when(serviceCategoryRepository.findById(anyString())).thenReturn(Optional.of(serviceCategory));
        when(serviceCategoryRepository.save(any(ServiceCategory.class))).thenReturn(serviceCategory);
        when(modelMapper.map(any(ServiceCategory.class), eq(ServiceCategoryDTO.class))).thenReturn(serviceCategoryDTO);
        when(multipartFile.getOriginalFilename()).thenReturn("sample.jpg");
        when(multipartFile.getBytes()).thenReturn(new byte[1]);

        ServiceCategoryDTO updatedCategory = serviceCategoryService.updateServiceCategory("SC1", serviceCategoryDTO, multipartFile);

        assertNotNull(updatedCategory);
        assertEquals("Web Development", updatedCategory.getName());
        verify(serviceCategoryRepository, times(1)).findById("SC1");
        verify(serviceCategoryRepository, times(1)).save(any(ServiceCategory.class));
    }

    @Test
    public void testDeleteServiceCategory() {
        doNothing().when(serviceCategoryRepository).deleteById(anyString());

        serviceCategoryService.deleteServiceCategory("SC1");

        verify(serviceCategoryRepository, times(1)).deleteById("SC1");
    }

    @Test
    public void testGetAllServiceCategories() {
        when(serviceCategoryRepository.findAll()).thenReturn(Arrays.asList(serviceCategory));
        when(modelMapper.map(any(ServiceCategory.class), eq(ServiceCategoryDTO.class))).thenReturn(serviceCategoryDTO);

        List<ServiceCategoryDTO> categories = serviceCategoryService.getAllServiceCategories();

        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertEquals("Web Development", categories.get(0).getName());
        verify(serviceCategoryRepository, times(1)).findAll();
    }
}

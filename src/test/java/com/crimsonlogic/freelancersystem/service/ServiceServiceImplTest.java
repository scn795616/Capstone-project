package com.crimsonlogic.freelancersystem.service;

import com.crimsonlogic.freelancersystem.dto.ServiceDTO;
import com.crimsonlogic.freelancersystem.dto.UserDTO;
import com.crimsonlogic.freelancersystem.entity.Service;
import com.crimsonlogic.freelancersystem.entity.ServiceCategory;
import com.crimsonlogic.freelancersystem.entity.User;
import com.crimsonlogic.freelancersystem.exception.ResourceNotFoundException;
import com.crimsonlogic.freelancersystem.repository.ServiceCategoryRepository;
import com.crimsonlogic.freelancersystem.repository.ServiceRepository;
import com.crimsonlogic.freelancersystem.serviceImpl.ServiceServiceImpl;

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

public class ServiceServiceImplTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private ServiceCategoryRepository serviceCategoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private ServiceServiceImpl serviceService;

    private Service service;
    private ServiceDTO serviceDTO;
    private UserDTO userDTO;
    private ServiceCategory serviceCategory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        serviceCategory = new ServiceCategory();
        serviceCategory.setId("SC1");
        serviceCategory.setName("Web Development");
        serviceCategory.setDescription("Development of websites and web applications");

        User user = new User();
        user.setId("U1");
        user.setUsername("testuser");
        user.setPassword("password123");

        service = new Service();
        service.setId("S1");
        service.setName("Website Design");
        service.setDescription("Designing responsive websites");
        service.setPrice(500.0);
        service.setImageUrl("http://localhost:3000/images/sample.jpg");
        service.setServiceCategory(serviceCategory);
        service.setUser(user);

        serviceDTO = new ServiceDTO();
        serviceDTO.setName("Website Design");
        serviceDTO.setDescription("Designing responsive websites");
        serviceDTO.setPrice(500.0);
        serviceDTO.setCategory("SC1");

        userDTO = new UserDTO();
        userDTO.setId("U1");
        userDTO.setUsername("testuser");
        userDTO.setPassword("password123");
    }

    @Test
    public void testCreateService() throws IOException {
        when(serviceCategoryRepository.findById(anyString())).thenReturn(Optional.of(serviceCategory));
        when(serviceRepository.save(any(Service.class))).thenReturn(service);
        when(multipartFile.getOriginalFilename()).thenReturn("sample.jpg");
        when(multipartFile.getBytes()).thenReturn(new byte[0]);

        Service createdService = serviceService.createService(serviceDTO, multipartFile, userDTO);

        assertNotNull(createdService);
        assertEquals("Website Design", createdService.getName());
        verify(serviceRepository, times(1)).save(any(Service.class));
    }

    @Test
    public void testGetServiceById() {
        when(serviceRepository.findById(anyString())).thenReturn(Optional.of(service));
        when(modelMapper.map(any(Service.class), eq(ServiceDTO.class))).thenReturn(serviceDTO);

        ServiceDTO foundService = serviceService.getServiceById("S1");

        assertNotNull(foundService);
        assertEquals("Website Design", foundService.getName());
        verify(serviceRepository, times(1)).findById("S1");
    }

    @Test
    public void testUpdateService() {
        when(serviceRepository.findById(anyString())).thenReturn(Optional.of(service));
        when(serviceRepository.save(any(Service.class))).thenReturn(service);
        when(modelMapper.map(any(Service.class), eq(ServiceDTO.class))).thenReturn(serviceDTO);

        ServiceDTO updatedService = serviceService.updateService("S1", serviceDTO);

        assertNotNull(updatedService);
        assertEquals("Website Design", updatedService.getName());
        verify(serviceRepository, times(1)).findById("S1");
        verify(serviceRepository, times(1)).save(any(Service.class));
    }

    @Test
    public void testDeleteService() {
        doNothing().when(serviceRepository).deleteById(anyString());

        serviceService.deleteService("S1");

        verify(serviceRepository, times(1)).deleteById("S1");
    }

    @Test
    public void testGetAllServices() {
        when(serviceRepository.findAll()).thenReturn(Arrays.asList(service));

        List<Service> services = serviceService.getAllServices();

        assertNotNull(services);
        assertEquals(1, services.size());
        assertEquals("Website Design", services.get(0).getName());
        verify(serviceRepository, times(1)).findAll();
    }

    @Test
    public void testFindByUser_UserId() {
        when(serviceRepository.findByUser_Id(anyString())).thenReturn(Arrays.asList(service));

        List<Service> services = serviceService.findByUser_UserId("U1");

        assertNotNull(services);
        assertEquals(1, services.size());
        assertEquals("Website Design", services.get(0).getName());
        verify(serviceRepository, times(1)).findByUser_Id("U1");
    }
}

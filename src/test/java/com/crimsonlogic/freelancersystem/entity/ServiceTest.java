package com.crimsonlogic.freelancersystem.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTest {

    private Service service;
    private ServiceCategory serviceCategory;
    private User user;

    @BeforeEach
    public void setUp() {
        serviceCategory = new ServiceCategory();
        serviceCategory.setId("SC1");
        serviceCategory.setName("Web Development");
        serviceCategory.setDescription("Development of websites and web applications");
        serviceCategory.setImageUrl("http://example.com/image.jpg");

        user = new User();
        user.setId("U1");
        user.setUsername("testuser");
        user.setPassword("password123");

        service = new Service();
        service.setName("Website Design");
        service.setDescription("Designing responsive websites");
        service.setPrice(500.0);
        service.setImageUrl("http://example.com/service.jpg");
        service.setServiceCategory(serviceCategory);
        service.setUser(user);
    }

    @Test
    public void testGenerateId() {
        service.generateId();
        assertNotNull(service.getId());
        assertEquals("US", service.getId().substring(0, 2));
    }

    @Test
    public void testServiceFields() {
        assertEquals("Website Design", service.getName());
        assertEquals("Designing responsive websites", service.getDescription());
        assertEquals(500.0, service.getPrice());
        assertEquals("http://example.com/service.jpg", service.getImageUrl());
        assertEquals(serviceCategory, service.getServiceCategory());
        assertEquals(user, service.getUser());
    }
}

package com.crimsonlogic.freelancersystem.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceCategoryTest {

    private ServiceCategory serviceCategory;

    @BeforeEach
    public void setUp() {
        serviceCategory = new ServiceCategory();
        serviceCategory.setName("Web Development");
        serviceCategory.setDescription("Development of websites and web applications");
        serviceCategory.setImageUrl("http://example.com/image.jpg");
    }

    @Test
    public void testGenerateId() {
        serviceCategory.generateId();
        assertNotNull(serviceCategory.getId());
        assertEquals("US", serviceCategory.getId().substring(0, 2));
    }

    @Test
    public void testServiceCategoryFields() {
        assertEquals("Web Development", serviceCategory.getName());
        assertEquals("Development of websites and web applications", serviceCategory.getDescription());
        assertEquals("http://example.com/image.jpg", serviceCategory.getImageUrl());
    }
}

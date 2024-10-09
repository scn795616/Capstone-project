package com.crimsonlogic.freelancersystem.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
    }

    @Test
    public void testGenerateId() {
        user.generateId();
        assertNotNull(user.getId());
        assertEquals("US", user.getId().substring(0, 2));
    }

    @Test
    public void testUserFields() {
        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
    }
}


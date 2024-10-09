package com.crimsonlogic.freelancersystem.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDetailsTest {

    private UserDetails userDetails;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId("U1");
        user.setUsername("testuser");
        user.setPassword("password123");

        userDetails = new UserDetails();
        userDetails.setFirstName("John");
        userDetails.setLastName("Doe");
        userDetails.setPhoneNumber("1234567890");
        userDetails.setEmail("john.doe@example.com");
        userDetails.setUser(user);
    }

    @Test
    public void testGenerateId() {
        userDetails.generateId();
        assertNotNull(userDetails.getId());
        assertEquals("US", userDetails.getId().substring(0, 2));
    }

    @Test
    public void testUserDetailsFields() {
        assertEquals("John", userDetails.getFirstName());
        assertEquals("Doe", userDetails.getLastName());
        assertEquals("1234567890", userDetails.getPhoneNumber());
        assertEquals("john.doe@example.com", userDetails.getEmail());
        assertEquals(user, userDetails.getUser());
    }
}

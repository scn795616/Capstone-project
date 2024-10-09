package com.crimsonlogic.freelancersystem.repository;

import com.crimsonlogic.freelancersystem.entity.User;
import com.crimsonlogic.freelancersystem.entity.UserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserDetailsRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    private User user;
    private UserDetails userDetails;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId("U1");
        user.setUsername("testuser");
        user.setPassword("password123");
        entityManager.persistAndFlush(user);

        userDetails = new UserDetails();
        userDetails.setId("UD1");
        userDetails.setFirstName("John");
        userDetails.setLastName("Doe");
        userDetails.setPhoneNumber("1234567890");
        userDetails.setEmail("john.doe@example.com");
        userDetails.setUser(user);
        entityManager.persistAndFlush(userDetails);
    }

    @Test
    public void testFindByUser_id() {
        // Debugging: Print all UserDetails in the database
        System.out.println("All UserDetails in the database:");
        userDetailsRepository.findAll().forEach(System.out::println);

        UserDetails foundUserDetails = userDetailsRepository.findByUser_id(user.getId());
        assertNotNull(foundUserDetails, "UserDetails should not be null");
        assertEquals("John", foundUserDetails.getFirstName());
        assertEquals("Doe", foundUserDetails.getLastName());
        assertEquals("1234567890", foundUserDetails.getPhoneNumber());
        assertEquals("john.doe@example.com", foundUserDetails.getEmail());
        assertEquals(user, foundUserDetails.getUser());
    }

    @Test
    public void testSaveUserDetails() {
        User newUser = new User();
        newUser.setId("U2");
        newUser.setUsername("newuser");
        newUser.setPassword("newpassword");
        entityManager.persistAndFlush(newUser);

        UserDetails newUserDetails = new UserDetails();
        newUserDetails.setId("UD2");
        newUserDetails.setFirstName("Jane");
        newUserDetails.setLastName("Smith");
        newUserDetails.setPhoneNumber("0987654321");
        newUserDetails.setEmail("jane.smith@example.com");
        newUserDetails.setUser(newUser);
        entityManager.persistAndFlush(newUserDetails);

        // Debugging: Print all UserDetails in the database
        System.out.println("All UserDetails in the database after saving newUserDetails:");
        userDetailsRepository.findAll().forEach(System.out::println);

        // Verify that the user details are persisted correctly using entityManager.find
        UserDetails persistedUserDetails = entityManager.find(UserDetails.class, userDetails.getId());
        assertNotNull(persistedUserDetails, "Persisted UserDetails should not be null");
        assertEquals("John", persistedUserDetails.getFirstName());
        assertEquals(user, persistedUserDetails.getUser());

    }
}

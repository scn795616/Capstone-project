package com.crimsonlogic.freelancersystem.repository;

import com.crimsonlogic.freelancersystem.entity.User;
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
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId("U1");
        user.setUsername("testuser");
        user.setPassword("password123");
        entityManager.persistAndFlush(user);
    }

    @Test
    public void testFindByUsername() {
        User foundUser = userRepository.findByUsername("testuser");
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
        assertEquals("password123", foundUser.getPassword());
    }

    @Test
    public void testSaveUser() {
        User newUser = new User();
        newUser.setId("U2");
        newUser.setUsername("newuser");
        newUser.setPassword("newpassword");
        entityManager.persistAndFlush(newUser);


    }
}

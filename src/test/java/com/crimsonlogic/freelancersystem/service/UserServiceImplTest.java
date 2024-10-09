package com.crimsonlogic.freelancersystem.service;

import com.crimsonlogic.freelancersystem.dto.UserDTO;
import com.crimsonlogic.freelancersystem.entity.Role;
import com.crimsonlogic.freelancersystem.entity.User;
import com.crimsonlogic.freelancersystem.entity.UserDetails;
import com.crimsonlogic.freelancersystem.exception.ResourceNotFoundException;
import com.crimsonlogic.freelancersystem.repository.RoleRepository;
import com.crimsonlogic.freelancersystem.repository.UserDetailsRepository;
import com.crimsonlogic.freelancersystem.repository.UserRepository;
import com.crimsonlogic.freelancersystem.service.UserService;
import com.crimsonlogic.freelancersystem.serviceImpl.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDetails userDetails;
    private Role role;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId("U1");
        user.setUsername("testuser");
        user.setPassword("password123");

        userDetails = new UserDetails();
        userDetails.setId("UD1");
        userDetails.setFirstName("John");
        userDetails.setLastName("Doe");
        userDetails.setPhoneNumber("1234567890");
        userDetails.setEmail("john.doe@example.com");
        userDetails.setUser(user);

        role = new Role();
        role.setName("USER");
        role.setUser(user);

        userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setPassword("password123");
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setPhoneNumber("1234567890");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setRole("USER");
    }

    @Test
    public void testCreateUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userDetailsRepository.save(any(UserDetails.class))).thenReturn(userDetails);
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDTO);

        UserDTO createdUser = userService.createUser(userDTO);

        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
        verify(userDetailsRepository, times(1)).save(any(UserDetails.class));
        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    public void testGetUserById() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDTO);

        UserDTO foundUser = userService.getUserById("U1");

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
        verify(userRepository, times(1)).findById("U1");
    }

    @Test
    public void testUpdateUser() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDTO);

        UserDTO updatedUser = userService.updateUser("U1", userDTO);

        assertNotNull(updatedUser);
        assertEquals("testuser", updatedUser.getUsername());
        verify(userRepository, times(1)).findById("U1");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userRepository).deleteById(anyString());

        userService.deleteUser("U1");

        verify(userRepository, times(1)).deleteById("U1");
    }

    @Test
    public void testLogin() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        when(roleRepository.findByUser(any(User.class))).thenReturn(role);
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDTO);

        UserDTO loggedInUser = userService.login(userDTO);

        assertNotNull(loggedInUser);
        assertEquals("testuser", loggedInUser.getUsername());
        assertEquals("USER", loggedInUser.getRole());
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(roleRepository, times(1)).findByUser(any(User.class));
    }

    @Test
    public void testLoginInvalidCredentials() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.login(userDTO);
        });

        verify(userRepository, times(1)).findByUsername("testuser");
    }
}

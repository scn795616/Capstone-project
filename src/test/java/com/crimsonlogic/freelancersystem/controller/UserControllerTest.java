package com.crimsonlogic.freelancersystem.controller;

import com.crimsonlogic.freelancersystem.dto.UserDTO;
import com.crimsonlogic.freelancersystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userDTO = new UserDTO();
        userDTO.setId("U1");
        userDTO.setUsername("testuser");
        userDTO.setPassword("password123");
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setPhoneNumber("1234567890");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setRole("USER");
    }

    @Test
    public void testCreateUser() throws Exception {
        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\",\"password\":\"password123\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"phoneNumber\":\"1234567890\",\"email\":\"john.doe@example.com\",\"role\":\"USER\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully!"));
    }

    @Test
    public void testGetUserById() throws Exception {
        when(userService.getUserById(anyString())).thenReturn(userDTO);

        mockMvc.perform(get("/api/users/U1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        when(userService.updateUser(anyString(), any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(put("/api/users/U1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\",\"password\":\"password123\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"phoneNumber\":\"1234567890\",\"email\":\"john.doe@example.com\",\"role\":\"USER\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(anyString());

        mockMvc.perform(delete("/api/users/U1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(userDTO));

        mockMvc.perform(get("/api/users/getalluser"))
                .andExpect(status().isOk());
    }


    @Test
    public void testLogin() throws Exception {
        when(userService.login(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\",\"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }
}

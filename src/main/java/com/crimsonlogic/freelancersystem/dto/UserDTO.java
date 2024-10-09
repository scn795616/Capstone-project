package com.crimsonlogic.freelancersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private String password; // You might want to handle this with care (e.g., encrypting).
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String role;
}

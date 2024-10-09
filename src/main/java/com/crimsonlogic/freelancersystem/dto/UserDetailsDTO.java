package com.crimsonlogic.freelancersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private UserDTO user;
}

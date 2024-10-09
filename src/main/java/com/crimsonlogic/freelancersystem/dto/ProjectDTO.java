package com.crimsonlogic.freelancersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private String id;
    private String name;
    private String description;
    private String status;
    private Double budget;
    private String category;
    private String imageUrl;
    private UserDTO client;
}

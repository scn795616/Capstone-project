package com.crimsonlogic.freelancersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceCategoryDTO {
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private byte[] image;
}

package com.crimsonlogic.freelancersystem.dto;

import com.crimsonlogic.freelancersystem.entity.ServiceCategory;
import com.crimsonlogic.freelancersystem.entity.User;

import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO {
    private String id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private String category;

     private ServiceCategory serviceCategory;

     private User freelancer;
}

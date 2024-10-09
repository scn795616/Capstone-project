package com.crimsonlogic.freelancersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequestDTO {
    private String id;
    private String serviceDescription;
    private Double price;
    private String status;
    private UserDTO client;
    private UserDTO freelancer;
    private ServiceDTO service;
}

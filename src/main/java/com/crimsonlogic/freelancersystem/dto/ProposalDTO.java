package com.crimsonlogic.freelancersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProposalDTO {
    private String id;
    private UserDTO freelancer;
    private ProjectDTO project;
    private String proposal;
    private Double price;
    private String estimatedTime;
    private String message;
    private String status;
}

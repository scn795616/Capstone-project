package com.crimsonlogic.freelancersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
    private String id;
    private String feedback;
    private Integer rating;
    private UserDTO client;
    private UserDTO freelancer;
    private ProjectDTO project;
}

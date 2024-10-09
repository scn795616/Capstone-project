package com.crimsonlogic.freelancersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private String id;
    private Double amount;
    private String paymentStatus;
    private UserDTO client;
    private UserDTO freelancer;
    private ServiceRequestDTO serviceRequest;
}

package com.crimsonlogic.freelancersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDTO {
    private String id;
    private Double amount;
    private UserDTO user;
}

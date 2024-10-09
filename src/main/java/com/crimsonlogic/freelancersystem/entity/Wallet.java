package com.crimsonlogic.freelancersystem.entity;

import java.math.BigDecimal;

import com.crimsonlogic.freelancersystem.util.CustomPrefixIdentifierGenerator;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    private String id;

    @Column(nullable = false)
    private Double balance;

    // Many-to-One relationship with User
    @ManyToOne
    @JoinColumn(name = "userdetail_id",referencedColumnName = "id")
    private UserDetails userDetails;
    
    private String status;

    @PrePersist
    public void generateId() {
        this.id = CustomPrefixIdentifierGenerator.generateId("WL");
    }
    // Constructors, Getters, Setters, etc.
}

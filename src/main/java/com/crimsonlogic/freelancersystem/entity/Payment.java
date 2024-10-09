package com.crimsonlogic.freelancersystem.entity;

import com.crimsonlogic.freelancersystem.util.CustomPrefixIdentifierGenerator;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    private String id;  // Custom ID with prefix "TX"

    private Double amount;
    private String paymentStatus;

    @ManyToOne
    @JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "FK_CLIENT_USER"))
    private User client;

    @ManyToOne
    @JoinColumn(name = "freelancer_id", foreignKey = @ForeignKey(name = "FK_FREELANCER_USER"))
    private User freelancer;

    @ManyToOne
    @JoinColumn(name = "service_request_id", foreignKey = @ForeignKey(name = "FK_SERVICE_REQUEST"))
    private ServiceRequest serviceRequest;

    @PrePersist
    public void generateId() {
        this.id = CustomPrefixIdentifierGenerator.generateId("US");
    }
}


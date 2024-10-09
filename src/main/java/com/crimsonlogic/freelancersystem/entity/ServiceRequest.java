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
@Table(name = "service_requests")
public class ServiceRequest {

    @Id
    private String id;  // Custom ID with prefix "SR"

    private String serviceDescription;
    
    @Column(nullable = false)
    private Double price;
    
    @Column(nullable = false)
    private String status="pending";

    @ManyToOne
    @JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "FK_CLIENT_USER"))
    private User client;

    @ManyToOne
    @JoinColumn(name = "freelancer_id", foreignKey = @ForeignKey(name = "FK_FREELANCER_USER"))
    private User freelancer;

    @ManyToOne
    @JoinColumn(name = "service_id", foreignKey = @ForeignKey(name = "FK_SERVICE_REQUEST"))
    private Service service;
    

    @PrePersist
    public void generateId() {
        this.id = CustomPrefixIdentifierGenerator.generateId("US");
    }
}


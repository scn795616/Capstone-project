package com.crimsonlogic.freelancersystem.entity;

import java.util.List;

import com.crimsonlogic.freelancersystem.util.CustomPrefixIdentifierGenerator;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proposals")
public class Proposal {

    @Id
    private String id;  // Custom ID with prefix "PRP"

    @ManyToOne
    @JoinColumn(name = "freelancer_id", foreignKey = @ForeignKey(name = "FK_FREELANCER_USERDetails"))
    private UserDetails freelancer;
    
    @ManyToOne
    @JoinColumn(name = "client_id",referencedColumnName = "id")
    private UserDetails client;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(nullable = false)
    private String proposal;
    
    @Column(nullable = false)
    private Double price;
    
    @Column(nullable = false)
    private String status="pending";
    
    @Column(nullable = false)
    private String estimatedTime;
    
    @Column(nullable = true)
    private String customerMessage;
    
    @ManyToOne
    @JoinColumn(name = "serviceRequet_id",referencedColumnName = "id")
    private ServiceRequest serviceRequest;


    @PrePersist
    public void generateId() {
        this.id = CustomPrefixIdentifierGenerator.generateId("US");
    }
}


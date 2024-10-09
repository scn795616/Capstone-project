package com.crimsonlogic.freelancersystem.entity;

import java.util.List;

import com.crimsonlogic.freelancersystem.util.CustomPrefixIdentifierGenerator;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "services")
public class Service {

    @Id
    private String id;  // Custom ID with prefix "SRV"

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private Double price;
    
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "serviceCategory_id", referencedColumnName = "id")
    
    private ServiceCategory serviceCategory;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    
    private User user;

    @PrePersist
    public void generateId() {
        this.id = CustomPrefixIdentifierGenerator.generateId("US");
    }
}


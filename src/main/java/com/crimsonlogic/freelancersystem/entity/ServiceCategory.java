package com.crimsonlogic.freelancersystem.entity;


import java.util.List;

import com.crimsonlogic.freelancersystem.util.CustomPrefixIdentifierGenerator;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "service_categories")
public class ServiceCategory {

    @Id
    private String id;  // Custom ID with prefix "SC"

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String description;
    
    
    private String imageUrl;


    @PrePersist
    public void generateId() {
        this.id = CustomPrefixIdentifierGenerator.generateId("US");
    }
}


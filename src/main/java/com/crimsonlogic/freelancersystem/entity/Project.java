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
@Table(name = "projects")
public class Project {

	 @Id
    private String id;  // Custom ID with prefix "PR"

	 @Column(nullable = false)
    private String name;
	 
	 @Column(nullable = false)
    private String description;
	 
	 @Column(nullable = false)
	 private String status="Open";


	 @Column(nullable = false)
	    private Double budget;
	 
	 private String imageUrl;
	 
    @ManyToOne
    @JoinColumn(name = "client_id")
    private UserDetails client;

    @ManyToOne
    @JoinColumn(name = "serviceCategory_id", referencedColumnName = "id")
    private ServiceCategory serviceCategory;

    @PrePersist
    public void generateId() {
        this.id = CustomPrefixIdentifierGenerator.generateId("US");
    }
}

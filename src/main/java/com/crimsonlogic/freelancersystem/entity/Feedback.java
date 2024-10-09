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
@Table(name = "feedbacks")
public class Feedback {

    @Id
    private String id;  // Custom ID with prefix "FB"

    private String feedbackText;
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "FK_CLIENT_USER"))
    private User client;

    @ManyToOne
    @JoinColumn(name = "freelancer_id", foreignKey = @ForeignKey(name = "FK_FREELANCER_USER"))
    private User freelancer;
    
    @ManyToOne
    @JoinColumn(name = "project_id", foreignKey = @ForeignKey(name = "FK_PROJECT_FEEDBACK"))
    private Project project;

    @PrePersist
    public void generateId() {
        this.id = CustomPrefixIdentifierGenerator.generateId("US");
    }
}


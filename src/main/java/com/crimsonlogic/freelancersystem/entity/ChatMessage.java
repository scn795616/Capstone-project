package com.crimsonlogic.freelancersystem.entity;

import java.sql.Timestamp;

import com.crimsonlogic.freelancersystem.util.CustomPrefixIdentifierGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChatMessage {
    @Id
    private String id;
    private String senderId;
    private String receiverId;
    private String message;
    private Timestamp timestamp;
    // getters and setters
    
    @PrePersist
    public void generateId() {
        this.id = CustomPrefixIdentifierGenerator.generateId("CH");
    }
}

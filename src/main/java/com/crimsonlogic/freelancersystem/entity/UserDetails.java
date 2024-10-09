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
@Table(name = "user_details")
public class UserDetails {

	@Id
    private String id;  // This can also have a custom prefix like "UD" for UserDetails

	@Column(nullable = false)
    private String firstName;
	
	@Column(nullable = false)
    private String lastName;
	
	@Column(nullable = false)
    private String phoneNumber;
	
	@Column(nullable = false)
    private String email;

    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER"))
    private User user;  // The reference back to the User entity
   
    @PrePersist
    public void generateId() {
        this.id = CustomPrefixIdentifierGenerator.generateId("US");
    }
}


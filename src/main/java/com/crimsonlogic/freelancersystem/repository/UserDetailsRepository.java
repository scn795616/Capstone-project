package com.crimsonlogic.freelancersystem.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.crimsonlogic.freelancersystem.entity.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, String> {

	UserDetails findByUser_id(String id);
    // Additional query methods if needed
}


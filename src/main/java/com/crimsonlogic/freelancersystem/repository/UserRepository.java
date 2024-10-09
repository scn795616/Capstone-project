package com.crimsonlogic.freelancersystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crimsonlogic.freelancersystem.entity.User;

public interface UserRepository extends JpaRepository<User, String>{
	User findByUsername(String username);
}

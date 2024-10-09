package com.crimsonlogic.freelancersystem.repository;

import com.crimsonlogic.freelancersystem.entity.Role;
import com.crimsonlogic.freelancersystem.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

	Role findByUser(User user);
}

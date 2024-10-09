package com.crimsonlogic.freelancersystem.repository;

import com.crimsonlogic.freelancersystem.entity.Project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {

	List<Project> findByClient_UserId(String userId);
}

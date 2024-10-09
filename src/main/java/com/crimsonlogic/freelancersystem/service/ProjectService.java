package com.crimsonlogic.freelancersystem.service;

import com.crimsonlogic.freelancersystem.dto.ProjectDTO;
import com.crimsonlogic.freelancersystem.entity.Project;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ProjectService {
    Project createProject(ProjectDTO projectDTO, MultipartFile image, String userId);
    ProjectDTO getProjectById(String id);
    ProjectDTO updateProject(String id, ProjectDTO projectDTO);
    void deleteProject(String id);
	List<Project> getAllProjects();
	List<Project> getAllProjectsByUserId(String userId);
}

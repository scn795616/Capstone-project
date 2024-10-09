package com.crimsonlogic.freelancersystem.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.crimsonlogic.freelancersystem.dto.ProjectDTO;
import com.crimsonlogic.freelancersystem.dto.ServiceDTO;
import com.crimsonlogic.freelancersystem.entity.Project;
import com.crimsonlogic.freelancersystem.service.ProjectService;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("create/{id}")
    public ResponseEntity<Project> createProject(@RequestPart("project") ProjectDTO projectDTO,
    		@RequestPart("image") MultipartFile image,@PathVariable("id")String userId) throws IOException {
        Project createdProject = projectService.createProject(projectDTO,image,userId);
        return ResponseEntity.ok(createdProject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable String id) {
        ProjectDTO projectDTO = projectService.getProjectById(id);
        return ResponseEntity.ok(projectDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable String id, @RequestBody ProjectDTO projectDTO) {
        ProjectDTO updatedProject = projectService.updateProject(id, projectDTO);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("list")
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }
    
    @GetMapping("list/{id}")
    public ResponseEntity<List<Project>> getAllProjectsByUserId(@PathVariable("id")String userId) {
        List<Project> projects = projectService.getAllProjectsByUserId(userId);
        return ResponseEntity.ok(projects);
    }
}


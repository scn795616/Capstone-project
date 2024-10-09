package com.crimsonlogic.freelancersystem.serviceImpl;

import com.crimsonlogic.freelancersystem.dto.ProjectDTO;
import com.crimsonlogic.freelancersystem.dto.UserDTO;
import com.crimsonlogic.freelancersystem.entity.Project;
import com.crimsonlogic.freelancersystem.exception.ResourceNotFoundException;
import com.crimsonlogic.freelancersystem.repository.ProjectRepository;
import com.crimsonlogic.freelancersystem.repository.ServiceCategoryRepository;
import com.crimsonlogic.freelancersystem.repository.UserDetailsRepository;
import com.crimsonlogic.freelancersystem.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {
	
    private static final String FOLDER_PATH = "D:\\Training 2024\\reactexample\\freelancing\\public\\images";


    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    
    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Project createProject(ProjectDTO projectDTO,MultipartFile file,String userId) {
        
    	Project project=new Project();
    	String imageUrl="http://localhost:3000/images/";
    	try {
			imageUrl = imageUrl+uploadImageToFileSystem(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	project.setBudget(projectDTO.getBudget());
    	project.setDescription(projectDTO.getDescription());
    	project.setName(projectDTO.getName());
    	project.setClient(userDetailsRepository.findByUser_id(userId));
    	project.setImageUrl(imageUrl);
    	project.setServiceCategory(serviceCategoryRepository.findById(projectDTO.getCategory()).get());
    	
        projectRepository.save(project);
        return project;
    }

    @Override
    public ProjectDTO getProjectById(String id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return modelMapper.map(project, ProjectDTO.class);
    }

    @Override
    public ProjectDTO updateProject(String id, ProjectDTO projectDTO) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        modelMapper.map(projectDTO, existingProject);
        projectRepository.save(existingProject);
        return modelMapper.map(existingProject, ProjectDTO.class);
    }

    @Override
    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }

	@Override
	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}
	
	 private String uploadImageToFileSystem(MultipartFile file) throws IOException {
	        // Create the directory if it doesn't exist
	        File directory = new File(FOLDER_PATH);
	        if (!directory.exists()) {
	            directory.mkdirs();
	        }

	        // Generate a unique filename
	        String originalFilename = file.getOriginalFilename();
	        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
	        String uniqueFilename = UUID.randomUUID().toString() + extension;

	        // Save the image file to the directory
	        Path filePath = Paths.get(FOLDER_PATH, uniqueFilename);
	        Files.write(filePath, file.getBytes());

	        // Return the path or URL to the saved image
	        return uniqueFilename; // or return a URL if you are serving the images via a web server
	    }

	@Override
	public List<Project> getAllProjectsByUserId(String userId) {
		return projectRepository.findByClient_UserId(userId);
	}
}

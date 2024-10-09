package com.crimsonlogic.freelancersystem.serviceImpl;

import com.crimsonlogic.freelancersystem.dto.ServiceCategoryDTO;
import com.crimsonlogic.freelancersystem.dto.ServiceDTO;
import com.crimsonlogic.freelancersystem.dto.UserDTO;
import com.crimsonlogic.freelancersystem.entity.Service;
import com.crimsonlogic.freelancersystem.entity.ServiceCategory;
import com.crimsonlogic.freelancersystem.entity.User;
import com.crimsonlogic.freelancersystem.exception.ResourceNotFoundException;
import com.crimsonlogic.freelancersystem.repository.ServiceCategoryRepository;
import com.crimsonlogic.freelancersystem.repository.ServiceRepository;
import com.crimsonlogic.freelancersystem.service.ServiceService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {
	
    private static final String FOLDER_PATH = "D:\\Training 2024\\reactexample\\freelancing\\public\\images";


    @Autowired
    private ServiceRepository serviceRepository;
    
    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Service createService(ServiceDTO serviceDTO,MultipartFile image,UserDTO userDto) {
    	Service service=new Service();
        // Save the image to a location and set the URL
        String imageUrl="http://localhost:3000/images/";
		try {
			imageUrl = imageUrl+uploadImageToFileSystem(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("hiiiiii");
		service.setDescription(serviceDTO.getDescription());
		service.setName(serviceDTO.getName());
		service.setPrice(serviceDTO.getPrice());
		service.setImageUrl(imageUrl);
//		System.out.println(freelancer);
		
		User user=new User();
		user.setId(userDto.getId());
		user.setPassword(userDto.getPassword());
		user.setUsername(userDto.getUsername());
		service.setUser(user);
		service.setServiceCategory(serviceCategoryRepository.findById(serviceDTO.getCategory()).get());
        serviceRepository.save(service);
        return service;
    }


	@Override
    public ServiceDTO getServiceById(String id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));
        return modelMapper.map(service, ServiceDTO.class);
    }

    @Override
    public ServiceDTO updateService(String id, ServiceDTO serviceDTO) {
        Service existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));
        modelMapper.map(serviceDTO, existingService);
        serviceRepository.save(existingService);
        return modelMapper.map(existingService, ServiceDTO.class);
    }

    @Override
    public void deleteService(String id) {
        serviceRepository.deleteById(id);
    }

	@Override
	public List<Service> getAllServices() {
		return serviceRepository.findAll();
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
	public List<Service> findByUser_UserId(String id) {
		// TODO Auto-generated method stub
		return serviceRepository.findByUser_Id(id);
	}
}

package com.crimsonlogic.freelancersystem.serviceImpl;

import com.crimsonlogic.freelancersystem.dto.ServiceCategoryDTO;
import com.crimsonlogic.freelancersystem.dto.UserDetailsDTO;
import com.crimsonlogic.freelancersystem.entity.ServiceCategory;
import com.crimsonlogic.freelancersystem.exception.ResourceNotFoundException;
import com.crimsonlogic.freelancersystem.repository.ServiceCategoryRepository;
import com.crimsonlogic.freelancersystem.service.ServiceCategoryService;
import org.modelmapper.ModelMapper;
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

@Service
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

    private static final String FOLDER_PATH = "D:\\Training 2024\\reactexample\\freelancing\\public\\images";

    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ServiceCategoryDTO createServiceCategory(ServiceCategoryDTO serviceCategoryDTO, MultipartFile image) {
        ServiceCategory serviceCategory = modelMapper.map(serviceCategoryDTO, ServiceCategory.class);
        // Save the image to a location and set the URL
        String imageUrl="http://localhost:3000/images/";
		try {
			imageUrl = imageUrl+uploadImageToFileSystem(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        serviceCategory.setImageUrl(imageUrl);
        serviceCategoryRepository.save(serviceCategory);
        return modelMapper.map(serviceCategory, ServiceCategoryDTO.class);
    }

    @Override
    public ServiceCategoryDTO getServiceCategoryById(String id) {
        ServiceCategory serviceCategory = serviceCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service Category not found"));
        return modelMapper.map(serviceCategory, ServiceCategoryDTO.class);
    }

    @Override
    public ServiceCategoryDTO updateServiceCategory(String id, ServiceCategoryDTO serviceCategoryDTO, MultipartFile image) {
        ServiceCategory existingCategory = serviceCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service Category not found"));
        modelMapper.map(serviceCategoryDTO, existingCategory);
        if (image != null && !image.isEmpty()) {
            String imageUrl="";
			try {
				imageUrl = uploadImageToFileSystem(image);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            existingCategory.setImageUrl(imageUrl);
        }
        serviceCategoryRepository.save(existingCategory);
        return modelMapper.map(existingCategory, ServiceCategoryDTO.class);
    }

    @Override
    public void deleteServiceCategory(String id) {
        serviceCategoryRepository.deleteById(id);
    }

    @Override
    public List<ServiceCategoryDTO> getAllServiceCategories() {
    	 return serviceCategoryRepository.findAll()
                 .stream()
                 .map(user -> modelMapper.map(user, ServiceCategoryDTO.class))
                 .collect(Collectors.toList());
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

	
}

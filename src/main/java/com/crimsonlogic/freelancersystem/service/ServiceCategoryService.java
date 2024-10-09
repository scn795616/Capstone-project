package com.crimsonlogic.freelancersystem.service;

import com.crimsonlogic.freelancersystem.dto.ServiceCategoryDTO;
import com.crimsonlogic.freelancersystem.entity.ServiceCategory;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ServiceCategoryService {
    ServiceCategoryDTO createServiceCategory(ServiceCategoryDTO serviceCategoryDTO, MultipartFile image);
    ServiceCategoryDTO getServiceCategoryById(String id);
    ServiceCategoryDTO updateServiceCategory(String id, ServiceCategoryDTO serviceCategoryDTO, MultipartFile image);
    void deleteServiceCategory(String id);
	List<ServiceCategoryDTO> getAllServiceCategories();
}


package com.crimsonlogic.freelancersystem.controller;

import com.crimsonlogic.freelancersystem.dto.ServiceCategoryDTO;
import com.crimsonlogic.freelancersystem.service.ServiceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/service-categories")
public class ServiceCategoryController {

    @Autowired
    private ServiceCategoryService serviceCategoryService;

    @PostMapping("/create")
    public ResponseEntity<ServiceCategoryDTO> createServiceCategory(
            @RequestPart("serviceCategory") ServiceCategoryDTO serviceCategoryDTO,
            @RequestPart("image") MultipartFile image) throws IOException {
        ServiceCategoryDTO createdCategory = serviceCategoryService.createServiceCategory(serviceCategoryDTO, image);
        return ResponseEntity.ok(createdCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceCategoryDTO> getServiceCategoryById(@PathVariable String id) {
        ServiceCategoryDTO serviceCategoryDTO = serviceCategoryService.getServiceCategoryById(id);
        return ResponseEntity.ok(serviceCategoryDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceCategoryDTO> updateServiceCategory(
            @PathVariable String id,
            @RequestPart("serviceCategory") ServiceCategoryDTO serviceCategoryDTO,
            @RequestPart("image") MultipartFile image) throws IOException {
        ServiceCategoryDTO updatedCategory = serviceCategoryService.updateServiceCategory(id, serviceCategoryDTO, image);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceCategory(@PathVariable String id) {
        serviceCategoryService.deleteServiceCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<ServiceCategoryDTO>> getAllServiceCategories() {
        List<ServiceCategoryDTO> serviceCategories = serviceCategoryService.getAllServiceCategories();
        return ResponseEntity.ok(serviceCategories);
    }
    
    
    
}

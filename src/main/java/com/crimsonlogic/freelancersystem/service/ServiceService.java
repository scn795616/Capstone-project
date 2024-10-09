package com.crimsonlogic.freelancersystem.service;

import com.crimsonlogic.freelancersystem.dto.ServiceDTO;
import com.crimsonlogic.freelancersystem.dto.UserDTO;
import com.crimsonlogic.freelancersystem.entity.Service;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ServiceService {
    Service createService(ServiceDTO serviceDTO, MultipartFile image, UserDTO user);
    ServiceDTO getServiceById(String id);
    ServiceDTO updateService(String id, ServiceDTO serviceDTO);
    void deleteService(String id);
	List<Service> getAllServices();
	List<Service> findByUser_UserId(String id);
}


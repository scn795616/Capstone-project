package com.crimsonlogic.freelancersystem.service;

import com.crimsonlogic.freelancersystem.dto.ServiceRequestDTO;
import com.crimsonlogic.freelancersystem.entity.ServiceRequest;

import java.util.List;

public interface ServiceRequestService {
    ServiceRequest createServiceRequest(ServiceRequestDTO serviceRequestDTO, String userId, String id);
    ServiceRequestDTO getServiceRequestById(String id);
    ServiceRequestDTO updateServiceRequest(String id, ServiceRequestDTO serviceRequestDTO);
    void deleteServiceRequest(String id);
	List<ServiceRequest> getAllServiceRequests();
	List<ServiceRequest> getAllServiceRequestsBYUserId(String id);
	List<ServiceRequest> getAllServiceRequestsBYFreelancerId(String id);
}

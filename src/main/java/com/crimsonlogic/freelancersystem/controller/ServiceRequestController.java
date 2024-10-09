package com.crimsonlogic.freelancersystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crimsonlogic.freelancersystem.dto.ServiceRequestDTO;
import com.crimsonlogic.freelancersystem.entity.ServiceRequest;
import com.crimsonlogic.freelancersystem.service.ServiceRequestService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/service-requests")
public class ServiceRequestController {

    @Autowired
    private ServiceRequestService serviceRequestService;

    @PostMapping("create/{userId}/{clientId}")
    public ResponseEntity<ServiceRequest> createServiceRequest(@RequestBody ServiceRequestDTO serviceRequestDTO,
    		@PathVariable("userId")String userId,@PathVariable("clientId")String id) {
        ServiceRequest createdServiceRequest = serviceRequestService.createServiceRequest(serviceRequestDTO,userId,id);
        return ResponseEntity.ok(createdServiceRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceRequestDTO> getServiceRequestById(@PathVariable String id) {
        ServiceRequestDTO serviceRequestDTO = serviceRequestService.getServiceRequestById(id);
        return ResponseEntity.ok(serviceRequestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceRequestDTO> updateServiceRequest(@PathVariable String id, @RequestBody ServiceRequestDTO serviceRequestDTO) {
        ServiceRequestDTO updatedServiceRequest = serviceRequestService.updateServiceRequest(id, serviceRequestDTO);
        return ResponseEntity.ok(updatedServiceRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceRequest(@PathVariable String id) {
        serviceRequestService.deleteServiceRequest(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("list")
    public ResponseEntity<List<ServiceRequest>> getAllServiceRequests() {
        List<ServiceRequest> serviceRequests = serviceRequestService.getAllServiceRequests();
        return ResponseEntity.ok(serviceRequests);
    }
    
    @GetMapping("list/{id}")
    public ResponseEntity<List<ServiceRequest>> getAllServiceRequestsByUserID(@PathVariable("id")String id) {
        List<ServiceRequest> serviceRequests = serviceRequestService.getAllServiceRequestsBYUserId(id);
        return ResponseEntity.ok(serviceRequests);
    }
    
    @GetMapping("listrequest/{id}")
    public ResponseEntity<List<ServiceRequest>> getAllServiceRequestsByFreelancerID(@PathVariable("id")String id) {
        List<ServiceRequest> serviceRequests = serviceRequestService.getAllServiceRequestsBYFreelancerId(id);
        return ResponseEntity.ok(serviceRequests);
    }
}


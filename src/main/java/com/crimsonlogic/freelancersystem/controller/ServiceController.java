package com.crimsonlogic.freelancersystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.crimsonlogic.freelancersystem.dto.ServiceDTO;
import com.crimsonlogic.freelancersystem.dto.UserDTO;
import com.crimsonlogic.freelancersystem.entity.Service;
import com.crimsonlogic.freelancersystem.entity.User;
import com.crimsonlogic.freelancersystem.service.ServiceService;
import com.crimsonlogic.freelancersystem.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jdk.internal.org.jline.utils.Log;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;
    
    @Autowired
    private UserService userService;

    @PostMapping("create/{id}")
    public ResponseEntity<Service> createService(
    		@RequestPart("service") ServiceDTO serviceDTO,
    		@RequestPart("image") MultipartFile image,@PathVariable("id")String id) throws IOException {
    	UserDTO user=userService.getUserById(id);
    	Service createdService = serviceService.createService(serviceDTO,image,user);
        return ResponseEntity.ok(createdService);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceDTO> getServiceById(@PathVariable String id) {
        ServiceDTO serviceDTO = serviceService.getServiceById(id);
        return ResponseEntity.ok(serviceDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceDTO> updateService(@PathVariable String id, @RequestBody ServiceDTO serviceDTO) {
        ServiceDTO updatedService = serviceService.updateService(id, serviceDTO);
        return ResponseEntity.ok(updatedService);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable String id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("list")
    public ResponseEntity<List<Service>> getAllServices() {
    	
        List<Service> services = serviceService.getAllServices();
        System.out.println(services);
        return ResponseEntity.ok(services);
    }
    
    @GetMapping("list/{id}")
    public ResponseEntity<List<Service>> getAllServicesByID(@PathVariable("id")String id) {
    	List<Service> services=serviceService.findByUser_UserId(id);
    	return ResponseEntity.ok(services);
    
    }
}


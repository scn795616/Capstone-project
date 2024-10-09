package com.crimsonlogic.freelancersystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crimsonlogic.freelancersystem.dto.UserDetailsDTO;
import com.crimsonlogic.freelancersystem.service.UserDetailsService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user-details")
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping
    public ResponseEntity<UserDetailsDTO> createUserDetails(@RequestBody UserDetailsDTO userDetailsDTO) {
        UserDetailsDTO createdUserDetails = userDetailsService.createUserDetails(userDetailsDTO);
        return ResponseEntity.ok(createdUserDetails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> getUserDetailsById(@PathVariable String id) {
        UserDetailsDTO userDetailsDTO = userDetailsService.getUserDetailsById(id);
        return ResponseEntity.ok(userDetailsDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> updateUserDetails(@PathVariable String id, @RequestBody UserDetailsDTO userDetailsDTO) {
        UserDetailsDTO updatedUserDetails = userDetailsService.updateUserDetails(id, userDetailsDTO);
        return ResponseEntity.ok(updatedUserDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserDetails(@PathVariable String id) {
        userDetailsService.deleteUserDetails(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping
    public ResponseEntity<List<UserDetailsDTO>> getAllUserDetails() {
        List<UserDetailsDTO> userDetailsList = userDetailsService.getAllUserDetails();
        return ResponseEntity.ok(userDetailsList);
    }
}

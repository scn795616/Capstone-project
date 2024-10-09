package com.crimsonlogic.freelancersystem.service;

import com.crimsonlogic.freelancersystem.dto.UserDetailsDTO;
import com.crimsonlogic.freelancersystem.entity.UserDetails;

import java.util.List;

public interface UserDetailsService {
    UserDetailsDTO createUserDetails(UserDetailsDTO userDetailsDTO);
    UserDetailsDTO getUserDetailsById(String id);
    UserDetailsDTO updateUserDetails(String id, UserDetailsDTO userDetailsDTO);
    void deleteUserDetails(String id);
	List<UserDetailsDTO> getAllUserDetails();
}


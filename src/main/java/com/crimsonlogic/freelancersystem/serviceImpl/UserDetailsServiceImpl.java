package com.crimsonlogic.freelancersystem.serviceImpl;

import com.crimsonlogic.freelancersystem.dto.UserDTO;
import com.crimsonlogic.freelancersystem.dto.UserDetailsDTO;
import com.crimsonlogic.freelancersystem.entity.UserDetails;
import com.crimsonlogic.freelancersystem.exception.ResourceNotFoundException;
import com.crimsonlogic.freelancersystem.repository.UserDetailsRepository;
import com.crimsonlogic.freelancersystem.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetailsDTO createUserDetails(UserDetailsDTO userDetailsDTO) {
        UserDetails userDetails = modelMapper.map(userDetailsDTO, UserDetails.class);
        userDetailsRepository.save(userDetails);
        return modelMapper.map(userDetails, UserDetailsDTO.class);
    }

    @Override
    public UserDetailsDTO getUserDetailsById(String id) {
        UserDetails userDetails = userDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserDetails not found"));
        return modelMapper.map(userDetails, UserDetailsDTO.class);
    }

    @Override
    public UserDetailsDTO updateUserDetails(String id, UserDetailsDTO userDetailsDTO) {
        UserDetails existingUserDetails = userDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserDetails not found"));
        modelMapper.map(userDetailsDTO, existingUserDetails);
        userDetailsRepository.save(existingUserDetails);
        return modelMapper.map(existingUserDetails, UserDetailsDTO.class);
    }

    @Override
    public void deleteUserDetails(String id) {
        userDetailsRepository.deleteById(id);
    }

	@Override
	public List<UserDetailsDTO> getAllUserDetails() {
		return userDetailsRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDetailsDTO.class))
                .collect(Collectors.toList());
	}
}

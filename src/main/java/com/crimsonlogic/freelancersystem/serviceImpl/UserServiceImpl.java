package com.crimsonlogic.freelancersystem.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crimsonlogic.freelancersystem.dto.UserDTO;
import com.crimsonlogic.freelancersystem.entity.Role;
import com.crimsonlogic.freelancersystem.entity.User;
import com.crimsonlogic.freelancersystem.entity.UserDetails;
import com.crimsonlogic.freelancersystem.exception.ResourceNotFoundException;
import com.crimsonlogic.freelancersystem.repository.RoleRepository;
import com.crimsonlogic.freelancersystem.repository.UserDetailsRepository;
import com.crimsonlogic.freelancersystem.repository.UserRepository;
import com.crimsonlogic.freelancersystem.service.UserService;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
    	User fuser=userRepository.findByUsername(userDTO.getUsername());
    	if (fuser!=null) {
            throw new RuntimeException("Username already exists");
        }
//        User user = modelMapper.map(userDTO, User.class);
    	User user=new User();
    	user.setUsername(userDTO.getUsername());
    	String encodedPassword = Base64.getEncoder().encodeToString(userDTO.getPassword().getBytes());
    	user.setPassword(encodedPassword);
    	
        userRepository.save(user);
        
        UserDetails userDetails=new UserDetails();
        userDetails.setEmail(userDTO.getEmail());
        userDetails.setFirstName(userDTO.getFirstName());
        userDetails.setLastName(userDTO.getLastName());
        userDetails.setPhoneNumber(userDTO.getPhoneNumber());
        userDetails.setUser(user);
        userDetailsRepository.save(userDetails);
        
        Role role=new Role();
        role.setName(userDTO.getRole());
        role.setUser(user);
        roleRepository.save(role);
        
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(String id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        modelMapper.map(userDTO, existingUser);  // Update existing user with new DTO values
        userRepository.save(existingUser);
        return modelMapper.map(existingUser, UserDTO.class);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

	@Override
	public UserDTO login(UserDTO userDTO) {
		User user = userRepository.findByUsername(userDTO.getUsername());
        if (user != null && user.getPassword().equals(userDTO.getPassword())) {
            Role role = roleRepository.findByUser(user);
            UserDTO loggedInUser = modelMapper.map(user, UserDTO.class);
            loggedInUser.setRole(role.getName());
            return loggedInUser;
        } else {
            throw new ResourceNotFoundException("Invalid username or password");
        }
	}
}

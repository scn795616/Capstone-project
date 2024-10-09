package com.crimsonlogic.freelancersystem.serviceImpl;

import com.crimsonlogic.freelancersystem.dto.RoleDTO;
import com.crimsonlogic.freelancersystem.dto.UserDTO;
import com.crimsonlogic.freelancersystem.entity.Role;
import com.crimsonlogic.freelancersystem.exception.ResourceNotFoundException;
import com.crimsonlogic.freelancersystem.repository.RoleRepository;
import com.crimsonlogic.freelancersystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = modelMapper.map(roleDTO, Role.class);
        roleRepository.save(role);
        return modelMapper.map(role, RoleDTO.class);
    }

    @Override
    public RoleDTO getRoleById(String id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        return modelMapper.map(role, RoleDTO.class);
    }

    @Override
    public RoleDTO updateRole(String id, RoleDTO roleDTO) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        modelMapper.map(roleDTO, existingRole);
        roleRepository.save(existingRole);
        return modelMapper.map(existingRole, RoleDTO.class);
    }

    @Override
    public void deleteRole(String id) {
        roleRepository.deleteById(id);
    }

	@Override
	public List<RoleDTO> getAllRoles() {
		return roleRepository.findAll()
                .stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .collect(Collectors.toList());
	}
	
	
}

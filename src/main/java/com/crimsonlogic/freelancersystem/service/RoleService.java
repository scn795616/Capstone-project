package com.crimsonlogic.freelancersystem.service;

import com.crimsonlogic.freelancersystem.dto.RoleDTO;
import com.crimsonlogic.freelancersystem.entity.Role;

import java.util.List;

public interface RoleService {
    RoleDTO createRole(RoleDTO roleDTO);
    RoleDTO getRoleById(String id);
    RoleDTO updateRole(String id, RoleDTO roleDTO);
    
    void deleteRole(String id);
	List<RoleDTO> getAllRoles();
}

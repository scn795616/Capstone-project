package com.crimsonlogic.freelancersystem.repository;

import com.crimsonlogic.freelancersystem.entity.ServiceRequest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, String> {

	List<ServiceRequest> findByClientId(String id);

	List<ServiceRequest> findByFreelancerId(String id);
}

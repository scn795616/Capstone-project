package com.crimsonlogic.freelancersystem.serviceImpl;

import com.crimsonlogic.freelancersystem.dto.ServiceRequestDTO;
import com.crimsonlogic.freelancersystem.dto.UserDTO;
import com.crimsonlogic.freelancersystem.entity.ServiceRequest;
import com.crimsonlogic.freelancersystem.exception.ResourceNotFoundException;
import com.crimsonlogic.freelancersystem.repository.ServiceRepository;
import com.crimsonlogic.freelancersystem.repository.ServiceRequestRepository;
import com.crimsonlogic.freelancersystem.repository.UserRepository;
import com.crimsonlogic.freelancersystem.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ServiceRequestServiceImpl implements ServiceRequestService {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    
    @Autowired
    private ServiceRepository serviceRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ServiceRequest createServiceRequest(ServiceRequestDTO serviceRequestDTO,String userId,String id) {
    	
    	ServiceRequest serviceRequest=new ServiceRequest();
    	serviceRequest.setPrice(serviceRequestDTO.getPrice());
    	serviceRequest.setServiceDescription(serviceRequestDTO.getServiceDescription());
        serviceRequest.setService(serviceRepository.findById(userId).get());
        serviceRequest.setFreelancer(serviceRepository.findById(userId).get().getUser());
        serviceRequest.setClient(userRepository.findById(id).get());
        serviceRequestRepository.save(serviceRequest);
        return serviceRequest;
    }

    @Override
    public ServiceRequestDTO getServiceRequestById(String id) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ServiceRequest not found"));
        return modelMapper.map(serviceRequest, ServiceRequestDTO.class);
    }

    @Override
    public ServiceRequestDTO updateServiceRequest(String id, ServiceRequestDTO serviceRequestDTO) {
        ServiceRequest existingServiceRequest = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ServiceRequest not found"));
        modelMapper.map(serviceRequestDTO, existingServiceRequest);
        serviceRequestRepository.save(existingServiceRequest);
        return modelMapper.map(existingServiceRequest, ServiceRequestDTO.class);
    }

    @Override
    public void deleteServiceRequest(String id) {
        serviceRequestRepository.deleteById(id);
    }

	@Override
	public List<ServiceRequest> getAllServiceRequests() {
		return serviceRequestRepository.findAll();
	}

	@Override
	public List<ServiceRequest> getAllServiceRequestsBYUserId(String id) {
		// TODO Auto-generated method stub
		return serviceRequestRepository.findByClientId(id);
	}

	@Override
	public List<ServiceRequest> getAllServiceRequestsBYFreelancerId(String id) {
		// TODO Auto-generated method stub
		return serviceRequestRepository.findByFreelancerId(id);
	}
}

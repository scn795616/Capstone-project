package com.crimsonlogic.freelancersystem.serviceImpl;

import com.crimsonlogic.freelancersystem.dto.ProposalDTO;
import com.crimsonlogic.freelancersystem.dto.UserDTO;
import com.crimsonlogic.freelancersystem.entity.Project;
import com.crimsonlogic.freelancersystem.entity.Proposal;
import com.crimsonlogic.freelancersystem.entity.ServiceRequest;
import com.crimsonlogic.freelancersystem.entity.Wallet;
import com.crimsonlogic.freelancersystem.exception.ResourceNotFoundException;
import com.crimsonlogic.freelancersystem.repository.ProjectRepository;
import com.crimsonlogic.freelancersystem.repository.ProposalRepository;
import com.crimsonlogic.freelancersystem.repository.ServiceRequestRepository;
import com.crimsonlogic.freelancersystem.repository.UserDetailsRepository;
import com.crimsonlogic.freelancersystem.repository.WalletRepository;
import com.crimsonlogic.freelancersystem.service.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProposalServiceImpl implements ProposalService {

    @Autowired
    private ProposalRepository proposalRepository;
    
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Proposal createProposal(ProposalDTO proposalDTO,String id) {
        ServiceRequest serviceRequest=serviceRequestRepository.findById(id).get();
        
    	
    	Proposal proposal=new Proposal();
    	proposal.setPrice(proposalDTO.getPrice());
    	proposal.setProposal(proposalDTO.getProposal());
    	proposal.setEstimatedTime(proposalDTO.getEstimatedTime());
    	proposal.setServiceRequest(serviceRequest);
    	proposal.setClient(userDetailsRepository.findByUser_id(serviceRequest.getClient().getId()));
    	proposal.setFreelancer(userDetailsRepository.findByUser_id(serviceRequest.getFreelancer().getId()));
        proposalRepository.save(proposal);
        return proposal;
    }

    @Override
    public ProposalDTO getProposalById(String id) {
        Proposal proposal = proposalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proposal not found"));
        return modelMapper.map(proposal, ProposalDTO.class);
    }

    @Override
    public Proposal updateProposal(String id, ProposalDTO proposalDTO) {
        Proposal existingProposal=proposalRepository.findById(id).get();
        
        existingProposal.setCustomerMessage(proposalDTO.getMessage());
        existingProposal.setStatus(proposalDTO.getStatus());
        if(proposalDTO.getStatus().equals("Approved")) {
        	if(existingProposal.getServiceRequest()!=null) {
        		ServiceRequest serviceRequest=serviceRequestRepository.findById(existingProposal.getServiceRequest().getId()).get();
            	serviceRequest.setStatus("Approved");
            	serviceRequestRepository.save(serviceRequest);
        	}
        	else {
        		Project project=projectRepository.findById(existingProposal.getProject().getId()).get();
        		project.setStatus("Closed");
        		projectRepository.save(project);
        	}
        	
            Wallet existingWallet =walletRepository.findByUserDetails_UserId(existingProposal.getClient().getUser().getId()).get();
            existingWallet.setBalance(existingWallet.getBalance()-existingProposal.getPrice());
            Wallet freelancerexistingWallet =walletRepository.findByUserDetails_UserId(existingProposal.getFreelancer().getUser().getId()).get();
            freelancerexistingWallet.setBalance(freelancerexistingWallet.getBalance()+existingProposal.getPrice());
            walletRepository.save(existingWallet);
            walletRepository.save(freelancerexistingWallet);
        	
        }
        
        proposalRepository.save(existingProposal);
        return existingProposal;
    }

    @Override
    public void deleteProposal(String id) {
        proposalRepository.deleteById(id);
    }

	@Override
	public List<Proposal> getAllProposalsByServiceRequestId(String id) {
		return proposalRepository.findByServiceRequest_id(id);
	}

	@Override
	public Proposal createProposalForProject(ProposalDTO proposalDTO, String id,String userId) {
		 Project project=projectRepository.findById(id).get();
	        
	    	
	    	Proposal proposal=new Proposal();
	    	proposal.setPrice(proposalDTO.getPrice());
	    	proposal.setProposal(proposalDTO.getProposal());
	    	proposal.setEstimatedTime(proposalDTO.getEstimatedTime());
	    	proposal.setProject(project);
	    	proposal.setClient(userDetailsRepository.findById(project.getClient().getId()).get());
	    	proposal.setFreelancer(userDetailsRepository.findByUser_id(userId));
	        proposalRepository.save(proposal);
	        return proposal;
	}

	@Override
	public List<Proposal> getAllProposalsByProjectId(String id,String userId) {
		return proposalRepository.findByProject_idAndFreelancer_UserId(id,userId);

	}

	@Override
	public List<Proposal> getAllProposalsByClientProjectId(String id) {
		return proposalRepository.findByProjectId(id);
	}
}

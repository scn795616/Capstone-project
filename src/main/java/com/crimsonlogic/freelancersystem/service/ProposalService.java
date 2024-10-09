package com.crimsonlogic.freelancersystem.service;

import com.crimsonlogic.freelancersystem.dto.ProposalDTO;
import com.crimsonlogic.freelancersystem.entity.Proposal;

import java.util.List;

public interface ProposalService {
    Proposal createProposal(ProposalDTO proposalDTO, String id);
    ProposalDTO getProposalById(String id);
    Proposal updateProposal(String id, ProposalDTO proposalDTO);
    void deleteProposal(String id);
	List<Proposal> getAllProposalsByServiceRequestId(String id);
	Proposal createProposalForProject(ProposalDTO proposalDTO, String id, String userId);
	List<Proposal> getAllProposalsByProjectId(String id, String userId);
	List<Proposal> getAllProposalsByClientProjectId(String id);
}


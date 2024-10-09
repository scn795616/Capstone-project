package com.crimsonlogic.freelancersystem.repository;

import com.crimsonlogic.freelancersystem.entity.Proposal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, String> {

	List<Proposal> findByServiceRequest_id(String id);

	List<Proposal> findByProject_idAndFreelancer_UserId(String id,String userId);

	List<Proposal> findByProjectId(String id);
}

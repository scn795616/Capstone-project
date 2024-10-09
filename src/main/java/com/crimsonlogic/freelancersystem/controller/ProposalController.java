package com.crimsonlogic.freelancersystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crimsonlogic.freelancersystem.dto.ProposalDTO;
import com.crimsonlogic.freelancersystem.entity.Proposal;
import com.crimsonlogic.freelancersystem.service.ProposalService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/proposals")
public class ProposalController {

    @Autowired
    private ProposalService proposalService;

    @PostMapping("create/{serviceId}")
    public ResponseEntity<Proposal> createProposal(@RequestBody ProposalDTO proposalDTO,
    		@PathVariable("serviceId")String id) {
        Proposal createdProposal = proposalService.createProposal(proposalDTO,id);
        return ResponseEntity.ok(createdProposal);
    }
    
    @PostMapping("createbyproject/{projectId}/{userId}")
    public ResponseEntity<Proposal> createProposalForProject(@RequestBody ProposalDTO proposalDTO,
    		@PathVariable("projectId")String id , @PathVariable("userId")String userId) {
        Proposal createdProposal = proposalService.createProposalForProject(proposalDTO,id,userId);
        return ResponseEntity.ok(createdProposal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProposalDTO> getProposalById(@PathVariable String id) {
        ProposalDTO proposalDTO = proposalService.getProposalById(id);
        return ResponseEntity.ok(proposalDTO);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Proposal> updateProposal(@PathVariable("id") String id, @RequestBody ProposalDTO proposalDTO) {
        Proposal updatedProposal = proposalService.updateProposal(id, proposalDTO);
        return ResponseEntity.ok(updatedProposal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProposal(@PathVariable String id) {
        proposalService.deleteProposal(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("list/{id}")
    public ResponseEntity<List<Proposal>> getAllProposals(@PathVariable("id")String id) {
        List<Proposal> proposals = proposalService.getAllProposalsByServiceRequestId(id);
        return ResponseEntity.ok(proposals);
    }
    
    @GetMapping("listprojects/{id}/{userId}")
    public ResponseEntity<List<Proposal>> getAllProposalsForProjects(@PathVariable("id")String id,@PathVariable("userId")String userId) {
        List<Proposal> proposals = proposalService.getAllProposalsByProjectId(id,userId);
        return ResponseEntity.ok(proposals);
    }
    
    @GetMapping("listprojects/{id}")
    public ResponseEntity<List<Proposal>> getAllProposalsForClientProjects(@PathVariable("id")String id) {
        List<Proposal> proposals = proposalService.getAllProposalsByClientProjectId(id);
        return ResponseEntity.ok(proposals);
    }
}


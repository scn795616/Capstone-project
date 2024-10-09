package com.crimsonlogic.freelancersystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crimsonlogic.freelancersystem.dto.FeedbackDTO;
import com.crimsonlogic.freelancersystem.service.FeedbackService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("create/{id}")
    public ResponseEntity<FeedbackDTO> createFeedback(@PathVariable("id")String id, @RequestBody FeedbackDTO feedbackDTO) {
        FeedbackDTO createdFeedback = feedbackService.createFeedback(id,feedbackDTO);
        return ResponseEntity.ok(createdFeedback);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDTO> getFeedbackById(@PathVariable String id) {
        FeedbackDTO feedbackDTO = feedbackService.getFeedbackById(id);
        return ResponseEntity.ok(feedbackDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackDTO> updateFeedback(@PathVariable String id, @RequestBody FeedbackDTO feedbackDTO) {
        FeedbackDTO updatedFeedback = feedbackService.updateFeedback(id, feedbackDTO);
        return ResponseEntity.ok(updatedFeedback);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable String id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacks() {
        List<FeedbackDTO> feedbacks = feedbackService.getAllFeedbacks();
        return ResponseEntity.ok(feedbacks);
    }
}

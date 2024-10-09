package com.crimsonlogic.freelancersystem.service;

import com.crimsonlogic.freelancersystem.dto.FeedbackDTO;
import com.crimsonlogic.freelancersystem.entity.Feedback;

import java.util.List;

public interface FeedbackService {
    FeedbackDTO createFeedback(String id, FeedbackDTO feedbackDTO);
    FeedbackDTO getFeedbackById(String id);
    FeedbackDTO updateFeedback(String id, FeedbackDTO feedbackDTO);
    void deleteFeedback(String id);
	List<FeedbackDTO> getAllFeedbacks();
}


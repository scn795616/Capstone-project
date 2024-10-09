package com.crimsonlogic.freelancersystem.serviceImpl;

import com.crimsonlogic.freelancersystem.dto.FeedbackDTO;
import com.crimsonlogic.freelancersystem.dto.UserDTO;
import com.crimsonlogic.freelancersystem.entity.Feedback;
import com.crimsonlogic.freelancersystem.exception.ResourceNotFoundException;
import com.crimsonlogic.freelancersystem.repository.FeedbackRepository;
import com.crimsonlogic.freelancersystem.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FeedbackDTO createFeedback(String id,FeedbackDTO feedbackDTO) {
        Feedback feedback = new Feedback();
        feedback.setFeedbackText(feedbackDTO.getFeedback());
        feedback.setRating(feedbackDTO.getRating());
        
        feedbackRepository.save(feedback);
        return modelMapper.map(feedback, FeedbackDTO.class);
    }

    @Override
    public FeedbackDTO getFeedbackById(String id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found"));
        return modelMapper.map(feedback, FeedbackDTO.class);
    }

    @Override
    public FeedbackDTO updateFeedback(String id, FeedbackDTO feedbackDTO) {
        Feedback existingFeedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found"));
        modelMapper.map(feedbackDTO, existingFeedback);
        feedbackRepository.save(existingFeedback);
        return modelMapper.map(existingFeedback, FeedbackDTO.class);
    }

    @Override
    public void deleteFeedback(String id) {
        feedbackRepository.deleteById(id);
    }

	@Override
	public List<FeedbackDTO> getAllFeedbacks() {
		return feedbackRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, FeedbackDTO.class))
                .collect(Collectors.toList());
	}
}

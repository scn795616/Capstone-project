package com.crimsonlogic.freelancersystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crimsonlogic.freelancersystem.entity.ChatMessage;
import com.crimsonlogic.freelancersystem.repository.ChatMessageRepository;

import java.util.List;

@Service
public class ChatMessageService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public List<ChatMessage> getChatMessages(String senderId, String receiverId) {
        return chatMessageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }

    public ChatMessage saveMessage(ChatMessage message) {
        return chatMessageRepository.save(message);
    }
}


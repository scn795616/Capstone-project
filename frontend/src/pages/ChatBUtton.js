// src/components/common/ChatButton.js
import React from 'react';

const ChatButton = ({ onClick }) => {
    return (
        <button className="chat-button" onClick={onClick}>
            💬
        </button>
    );
};

export default ChatButton;

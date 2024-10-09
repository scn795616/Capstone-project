// src/components/common/Chat.js
import React, { useState, useEffect } from 'react';
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import axios from 'axios';

const Chat = ({ currentUser, users, closeChat }) => {
    const [messages, setMessages] = useState([]);
    const [message, setMessage] = useState('');
    const [receiverId, setReceiverId] = useState(users[0].id);  // Default recipient for admin
    const [stompClient, setStompClient] = useState(null);

    // Fetch chat history when component mounts
    useEffect(() => {
        const fetchChatHistory = async () => {
            try {
                const response = await axios.get('/chat/history', {
                    params: {
                        senderId: currentUser.id,
                        receiverId: receiverId,
                    }
                });
                setMessages(response.data);
            } catch (error) {
                console.error("Error fetching chat history", error);
            }
        };

        fetchChatHistory();
    }, [currentUser.id, receiverId]);  // Fetch history when receiver changes

    // WebSocket connection
    useEffect(() => {
        const socket = new SockJS('/chat-websocket');
        const client = Stomp.over(socket);

        client.connect({}, () => {
            client.subscribe('/topic/messages', (message) => {
                const newMessage = JSON.parse(message.body);
                setMessages((prevMessages) => [...prevMessages, newMessage]);
            });
        });

        setStompClient(client);

        return () => {
            client.disconnect();
        };
    }, []);

    // Sending a message
    const sendMessage = () => {
        if (stompClient && message.trim()) {
            stompClient.send('/app/chat', {}, JSON.stringify({
                senderId: currentUser.id,
                receiverId: receiverId,
                message: message,
                timestamp: new Date()
            }));
            setMessage('');
        }
    };

    return (
        <div className="chat-container">
            <button className="close-chat" onClick={closeChat}>✖️</button>
            <div className="chat-messages">
                {messages.map((msg, index) => (
                    <div key={index} className={`message ${msg.senderId === currentUser.id ? 'sent' : 'received'}`}>
                        <span>{msg.message}</span>
                    </div>
                ))}
            </div>
            <div className="chat-input">
                <input
                    type="text"
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                    placeholder="Type a message..."
                />
                <button onClick={sendMessage}>Send</button>
            </div>
        </div>
        
    );
};

export default Chat;

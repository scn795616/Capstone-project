// ChatComponent.js
import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const ChatComponent = () => {
    const [message, setMessage] = useState('');
    const [messages, setMessages] = useState([]);
    const socket = new SockJS('http://localhost:8080/ws');
    const stompClient = Stomp.over(socket);
  
    useEffect(() => {
      stompClient.connect({}, () => {
        stompClient.subscribe('/topic/messages', (msg) => {
          setMessages((prevMessages) => [...prevMessages, JSON.parse(msg.body)]);
        });
      });
  
      return () => {
        stompClient.disconnect();
      };
    }, []);
  
    const sendMessage = () => {
      const msg = { from: 'Client', text: message };
      stompClient.send('/app/chat', {}, JSON.stringify(msg));
      setMessage('');
    };
  
    return (
      <Container className="chat-container">
        <Row className="chat-header">
          <Col>Chat</Col>
        </Row>
        <Row className="chat-body">
          <Col>
            {messages.map((msg, index) => (
              <div key={index} className="chat-message">
                <strong>{msg.from}:</strong> {msg.text}
              </div>
            ))}
          </Col>
        </Row>
        <Row className="chat-footer">
          <Col>
            <Form.Control
              type="text"
              value={message}
              onChange={(e) => setMessage(e.target.value)}
              placeholder="Type a message"
            />
          </Col>
          <Col xs="auto">
            <Button onClick={sendMessage}>Send</Button>
          </Col>
        </Row>
      <style jsx>
        {`
        /* Add this to your CSS file */
.chat-container {
  position: fixed;
  bottom: 0;
  right: 0;
  width: 50%;
  height: 50%;
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
}

.chat-header {
  background-color: #007bff;
  color: white;
  padding: 10px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}

.chat-body {
  flex: 1;
  padding: 10px;
  overflow-y: auto;
}

.chat-footer {
  display: flex;
  padding: 10px;
  border-top: 1px solid #ccc;
}

.chat-message {
  padding: 5px;
  margin-bottom: 5px;
  border-radius: 5px;
  background-color: #f1f1f1;
}

        `}
      </style>
    </Container>
  );
};

export default ChatComponent;

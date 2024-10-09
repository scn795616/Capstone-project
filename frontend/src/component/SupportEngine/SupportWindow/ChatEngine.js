import React, { useEffect, useState } from "react";

import { ChatEngineWrapper, Socket, ChatList, ChatFeed, ChatSettings } from 'react-chat-engine'
import { Col, Row, Container } from 'react-bootstrap'

const ChatEngine = props => {
    const [showChat, setShowChat] = useState(false);
    const user = JSON.parse(sessionStorage.getItem('userDetails'));
    const chatData = sessionStorage.getItem('chatData');
    console.log(chatData);
  

    useEffect(() => {
        if (props.visible) {
            setTimeout(() => {
                setShowChat(true)
            }, 500)
        }
    })

    return (
        <div
            className='transition-5'
            style={{
                    height: props.visible ? '100%' : '0px',
                    zIndex: props.visible ? '100' : '0',
                    width:'100%',
                    backgroundColor:'white'
            }}
        >
            {
                showChat &&
                <ChatEngineWrapper>
                    <Socket 
                        projectID='127fc0cb-2ee7-4515-861c-8ebb28786248'
                        userName={user.username}
                        userSecret={user.password}
                    />
                    
                    <ChatFeed activeChat={props.chat.id} />
                </ChatEngineWrapper>
            }
        </div>
    )
}

export default ChatEngine;

const styles = {
    chatEngineWindow: {
        width: '100%',  
        backgroundColor: '#fff',
    }
}
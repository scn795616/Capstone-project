import React, { useState } from "react"

import { styles } from "../styles";
import EmailForm from "./EmailForm";
import ChatEngine from "./ChatEngine";

const SupportWindow = props => {
    const [user, setUser] = useState(null)
    const [chat, setChat] = useState(null)
    return (
        <div 
            className='transition-5'
            style={{
                ...styles.supportWindow,
                ...{ opacity: props.visible ? '1' : '0' }
            }}
        >
            <EmailForm/>
            <EmailForm 
                setUser={user => setUser(user)} 
                setChat={chat => setChat(chat)} 
                visible={user === null || chat === null}

            />
            
             <ChatEngine 
                visible={user !== null && chat !== null}
                user={user} 
                chat={chat} 
            />
        </div>
    )
}

export default SupportWindow;
import React, { useState } from "react"

import { styles } from "../styles"
import axios from 'axios'


// import { LoadingOutlined } from '@ant-design/icons'

import Avatar from '../Avatar'

const EmailForm = props => {    
    const [email, setEmail] = useState('')
    const [loading, setLoading] = useState(false)

    const user = JSON.parse(sessionStorage.getItem('userDetails'));
    console.log(user);
    function getOrCreateUser(callback) {

        axios.put(
            'https://api.chatengine.io/users/',
            {username: user.username, email: email, secret: user.password},
            {headers: {"Private-Key": 'b549ccc7-7163-4a7d-8cf1-119d592db951'}}
        )
        .then(r => callback(r.data))
        .catch(e => console.log('Get or create user error', e))
    }


    function getOrCreateChat(callback) {
        axios.put(
            'https://api.chatengine.io/chats/',
            {usernames: [user.username, 'scn8910'], is_direct_chat: true},
            {headers: {
                "Project-ID": '127fc0cb-2ee7-4515-861c-8ebb28786248',
                "User-Name": user.username,
                "User-Secret": user.password,
            }}
        )
        .then(r => callback(r.data))
        .catch(e => console.log('Get or create chat error', e))
    }

    function handleSubmit(event) {
        event.preventDefault();

        console.log('Sending Email', email)

        getOrCreateUser(
            user => {
                props.setUser(user)
                getOrCreateChat(chat => {
                    console.log(chat);
                     props.setChat(chat)
                })
            }
        )
    }

    return (
        <div 
            style={{
                ...styles.emailFormWindow,
                ...{ 
                    height: props.visible ? '100%' : '0px',
                    opacity: props.visible ? '1' : '0'
                }
            }}
        >
            <div style={{ height: '0px' }}>
                <div style={styles.stripe} />
            </div>

            {/* <div 
                className='transition-5'
                style={{
                    ...styles.loadingDiv,
                    ...{ 
                        zIndex: loading ? '10' : '-1',
                        opacity: loading ? '0.33' : '0',
                    }
                }}
            /> */}
            {/* <LoadingOutlined
                className='transition-5'
                style={{
                    ...styles.loadingIcon,
                    ...{ 
                        zIndex: loading ? '10' : '-1',
                        opacity: loading ? '1' : '0',
                        fontSize: '82px',
                        top: 'calc(50% - 41px)', 
                        left: 'calc(50% - 41px)',  
                    }
                }}
            /> */}

            <div style={{ position: 'absolute', height: '100%', width: '100%', textAlign: 'center' }}>
                <Avatar 
                    style={{ 
                        position: 'relative',
                        left: 'calc(50% - 44px)',
                        top: '10%',
                    }}
                />

                <div style={styles.topText}>
                    Welcome to my <br /> support 👋
                </div>

                <form 
                    onSubmit={e => handleSubmit(e)}
                    style={{ position: 'relative', width: '100%', top: '19.75%' }}
                >
                    <input 
                        placeholder='Your Email'
                        onChange={e => setEmail(e.target.value)}
                        style={styles.emailInput}
                    />
                </form>

                <div style={styles.bottomText}>
                    Enter your email <br /> to get started.
                </div>
            </div>
        </div>
    )
}

export default EmailForm;
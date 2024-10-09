import React, { useState, useEffect } from 'react';
import api from '../../services/api';
import { Card, Container, Row, Col } from "react-bootstrap";

const Profile = () => {
    const [wallet, setWallet] = useState(null);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchWallet = async () => {
            try {
                const user = JSON.parse(sessionStorage.getItem('userDetails'));
                console.log(user.id);
                const response = await api.get(`http://localhost:8989/api/wallets/list/${user.id}`, {
                    headers: { 'Content-Type': 'application/json' },
                });
                console.log(response.data);  // Inspecting structure
                setWallet(response.data);   // Setting the wallet data directly
            } catch (error) {
                setError('Error while fetching the data');
            }
        };
        fetchWallet();
    }, []);

    return (
        <Container>
            <Row className="justify-content-md-center">
                <Col md={8}>
                    {wallet ? (
                        <Card className="profile-card">
                            <Card.Body>
                                <Card.Title className="profile-header text-center">Profile</Card.Title>
                                <Row>
                                    <Col md={6}>
                                        <div className="profile-info">
                                            <strong className="text-success">Username:</strong>
                                            <p>{wallet.userDetails.firstName}</p>
                                        </div>
                                    </Col>
                                    <Col md={6}>
                                        <div className="profile-info">
                                            <strong className="text-success">Email:</strong>
                                            <p>{wallet.userDetails.email}</p>
                                        </div>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col md={6}>
                                        <div className="profile-info">
                                            <strong className="text-success">First Name:</strong>
                                            <p>{wallet.userDetails.firstName}</p>
                                        </div>
                                    </Col>
                                    <Col md={6}>
                                        <div className="profile-info">
                                            <strong className="text-success">Last Name:</strong>
                                            <p>{wallet.userDetails.lastName}</p>
                                        </div>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col md={6}>
                                        <div className="profile-info">
                                            <strong className="text-success">Phone Number:</strong>
                                            <p>{wallet.userDetails.phoneNumber}</p>
                                        </div>
                                    </Col>
                                    <Col md={6}>
                                        <div className="profile-info">
                                            <strong className="text-success">Balance:</strong>
                                            <p>{wallet.balance}</p>
                                        </div>
                                    </Col>
                                </Row>
                            </Card.Body>
                        </Card>
                    ) : (
                        <p>{error || "Loading..."}</p>
                    )}
                </Col>
            </Row>
            <style jsx>{`
                .profile-card {
                    border-radius: 15px;
                    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                    padding: 20px;
                    background-color: #ffffff; /* White background */
                }

                .profile-header {
                    background-color: green; /* Green background */
                    color: #ffffff; /* White text */
                    padding: 10px;
                    border-radius: 15px 15px 0 0; /* Rounded corners at the top */
                    margin: -20px -20px 20px -20px; /* Full width */
                }

                .text-success {
                    color: #28a745 !important; /* Green color for labels */
                }

                .profile-info {
                    margin-bottom: 15px;
                }

                .profile-info p {
                    margin: 0;
                    font-size: 1rem;
                }
            `}</style>
        </Container>
    );
};

export default Profile;

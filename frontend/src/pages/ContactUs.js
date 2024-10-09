import React, { useState } from 'react';
import { Container, Form, Button, Card, Row, Col, Alert } from 'react-bootstrap';
import SupportEngine from '../component/SupportEngine';

const ContactUs = () => {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [message, setMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const user = JSON.parse(sessionStorage.getItem('userDetails'));

    const handleSubmit = (e) => {
        e.preventDefault();
        // Here you would typically send the data to your backend
        console.log('Message submitted:', { name, email, message });
    };

    return (
        <Container>
            <Row className="justify-content-md-center">
                <Col md={6}>
                    <Card className="contact-us-card">
                        <Card.Body>
                            <h2 className="contact-us-header text-center">Contact Us</h2>
                            {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
                            <Form onSubmit={handleSubmit}>
                                <Form.Group controlId="formName">
                                    <Form.Label className="text-success">Name</Form.Label>
                                    <Form.Control
                                        type="text"
                                        placeholder="Enter your name"
                                        value={name}
                                        onChange={(e) => setName(e.target.value)} />
                                </Form.Group>
                                <Form.Group controlId="formEmail">
                                    <Form.Label className="text-success">Email</Form.Label>
                                    <Form.Control
                                        type="email"
                                        placeholder="Enter your email"
                                        value={email}
                                        onChange={(e) => setEmail(e.target.value)} />
                                </Form.Group>
                                <Form.Group controlId="formMessage">
                                    <Form.Label className="text-success">Message</Form.Label>
                                    <Form.Control
                                        as="textarea"
                                        rows={3}
                                        value={message}
                                        onChange={(e) => setMessage(e.target.value)} />
                                </Form.Group>
                                <Button variant="success" type="submit">
                                    Submit
                                </Button>
                            </Form>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
            {user.role !== 'Admin' && (
                <div>
                    <SupportEngine />
                </div>
            )}
            <style jsx>{`
                .contact-us-card {
                    border-radius: 15px;
                    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                    padding: 20px;
                    background-color: #ffffff; /* White background */
                }

                .contact-us-header {
                    background-color: green; /* Green background */
                    color: #ffffff; /* White text */
                    padding: 10px;
                    border-radius: 15px 15px 0 0; /* Rounded corners at the top */
                    margin: -20px -20px 20px -20px; /* Full width */
                }

                .text-success {
                    color: #28a745 !important; /* Green color for labels */
                }

                button.btn-success {
                    width: 100%;
                    margin-top: 20px;
                }
            `}</style>
        </Container>
    );
};

export default ContactUs;

import React from "react";
import { useFormik } from "formik";
import * as Yup from "yup";
import api from "../../services/api"; // Import your Axios instance
import { useNavigate } from "react-router-dom";
import { Button, Form, Card, Container, Row, Col } from "react-bootstrap";

const Register = () => {
    const navigate = useNavigate();

    const formik = useFormik({
        initialValues: {
            username: "",
            password: "",
            firstName: "",
            lastName: "",
            phoneNumber: "",
            email: "",
            role: "Admin", // Default role (can change based on your logic)
        },
        validationSchema: Yup.object({
            username: Yup.string().required("Username is required"),
            password: Yup.string()
                .min(6, "Password must be at least 6 characters")
                .required("Password is required"),
            firstName: Yup.string().required("First name is required"),
            lastName: Yup.string().required("Last name is required"),
            phoneNumber: Yup.string().required("Phone number is required").max(10,"Phone number should be max 10digits"),
            email: Yup.string().email("Invalid email format").required("Email is required"),
        }),
        onSubmit: async (values) => {
            try {
                // Send POST request to register the user
                await api.post("/users/register", values);
                // Redirect to login page after successful registration
                navigate("/login");
            } catch (error) {
                console.error("Registration error:", error);
                // Handle error (e.g., show a message)
            }
        },
    });

    return (
        <Container>
            <Row className="justify-content-md-center">
                <Col md={8}>
                    <Card className="register-card">
                        <Card.Body>
                            <Card.Title className="register-header text-center">Register</Card.Title>
                            <Form onSubmit={formik.handleSubmit}>
                                <Row>
                                    <Col md={6}>
                                        <Form.Group controlId="formUsername">
                                            <Form.Label className="text-success">Username</Form.Label>
                                            <Form.Control
                                                type="text"
                                                placeholder="Enter username"
                                                {...formik.getFieldProps("username")}
                                                isInvalid={!!formik.errors.username && formik.touched.username}
                                            />
                                            <Form.Control.Feedback type="invalid">{formik.errors.username}</Form.Control.Feedback>
                                        </Form.Group>
                                    </Col>
                                    <Col md={6}>
                                        <Form.Group controlId="formPassword">
                                            <Form.Label className="text-success">Password</Form.Label>
                                            <Form.Control
                                                type="password"
                                                placeholder="Enter password"
                                                {...formik.getFieldProps("password")}
                                                isInvalid={!!formik.errors.password && formik.touched.password}
                                            />
                                            <Form.Control.Feedback type="invalid">{formik.errors.password}</Form.Control.Feedback>
                                        </Form.Group>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col md={6}>
                                        <Form.Group controlId="formFirstName">
                                            <Form.Label className="text-success">First Name</Form.Label>
                                            <Form.Control
                                                type="text"
                                                placeholder="Enter first name"
                                                {...formik.getFieldProps("firstName")}
                                                isInvalid={!!formik.errors.firstName && formik.touched.firstName}
                                            />
                                            <Form.Control.Feedback type="invalid">{formik.errors.firstName}</Form.Control.Feedback>
                                        </Form.Group>
                                    </Col>
                                    <Col md={6}>
                                        <Form.Group controlId="formLastName">
                                            <Form.Label className="text-success">Last Name</Form.Label>
                                            <Form.Control
                                                type="text"
                                                placeholder="Enter last name"
                                                {...formik.getFieldProps("lastName")}
                                                isInvalid={!!formik.errors.lastName && formik.touched.lastName}
                                            />
                                            <Form.Control.Feedback type="invalid">{formik.errors.lastName}</Form.Control.Feedback>
                                        </Form.Group>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col md={6}>
                                        <Form.Group controlId="formPhoneNumber">
                                            <Form.Label className="text-success">Phone Number</Form.Label>
                                            <Form.Control
                                                type="text"
                                                placeholder="Enter phone number"
                                                {...formik.getFieldProps("phoneNumber")}
                                                isInvalid={!!formik.errors.phoneNumber && formik.touched.phoneNumber}
                                            />
                                            <Form.Control.Feedback type="invalid">{formik.errors.phoneNumber}</Form.Control.Feedback>
                                        </Form.Group>
                                    </Col>
                                    <Col md={6}>
                                        <Form.Group controlId="formEmail">
                                            <Form.Label className="text-success">Email</Form.Label>
                                            <Form.Control
                                                type="email"
                                                placeholder="Enter email"
                                                {...formik.getFieldProps("email")}
                                                isInvalid={!!formik.errors.email && formik.touched.email}
                                            />
                                            <Form.Control.Feedback type="invalid">{formik.errors.email}</Form.Control.Feedback>
                                        </Form.Group>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col md={12}>

                                        <Form.Group controlId="formRole">
                                        <Form.Control
                                            type="hidden"
                                            value="Admin"
                                            {...formik.getFieldProps("role")}
                                        />
                                    </Form.Group>
                                    </Col>
                                </Row>
                                <Button variant="success" type="submit" className="mt-3">
                                    Register
                                </Button>
                            </Form>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
            <style>{`
                .register-card {
                    border-radius: 15px;
                    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                    padding: 20px;
                    background-color: #ffffff; /* White background */
                }

                .register-header {
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

export default Register;

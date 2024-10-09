import React, { useState } from 'react';
import api from "../../services/api";
import { useNavigate } from 'react-router-dom';
import { Formik } from 'formik';
import * as Yup from 'yup';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheckCircle } from '@fortawesome/free-solid-svg-icons';
import { Form, Button, Container, Row, Col, Card, Alert } from 'react-bootstrap';
import './Login.css'; // Import the custom CSS file

function Login({ setUserRole }) {
  const navigate = useNavigate();
  const [errorMessage, setErrorMessage] = useState('');
  const [success, setSuccess] = useState('');

  const initialValues = {
    username: '',
    password: '',
  };

  const validationSchema = Yup.object({
    username: Yup.string()
      .required('Username is required')
      .min(3, 'Username must be at least 3 characters long'),
    password: Yup.string()
      .required('Password is required')
      .min(6, 'Password must be at least 6 characters long'),
  });

  const handleLogin = async (values, { setSubmitting }) => {
    try {
      const response = await api.post('/users/login', values);
      const userDetails = response.data; // Assuming the response contains user details
      sessionStorage.setItem('userDetails', JSON.stringify(userDetails));
      const dat = JSON.parse(sessionStorage.getItem('userDetails'));
      console.log(dat.role);
      const role = response.data.role; // Assuming the response contains the user role
      setUserRole(role);
      setSuccess(<><FontAwesomeIcon icon={faCheckCircle} /> Logged in Successfully</>);
      setTimeout(() => {
        setSuccess('');
        navigate('/'); // Redirect to home or appropriate dashboard
      }, 2000); // Delay for 2 seconds
    } catch (error) {
      setErrorMessage('Login failed. Please check your username and password.');
      setTimeout(() => {
        setErrorMessage('');
      }, 2000); // Delay for 2 seconds
      console.error('Login failed:', error);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <Container style={{marginTop:'60px'}}>
      <Row className="justify-content-md-center">
        <Col md={6}>
          <Card className="login-card">
            <Card.Body>
              <h2 className="login-header text-center">Login</h2>
              {errorMessage && <div className="message error-message">{errorMessage}</div>}
              {success && <div className="message success-message">{success}</div>}
              <Formik
                initialValues={initialValues}
                validationSchema={validationSchema}
                onSubmit={handleLogin}
              >
                {({
                  values,
                  errors,
                  touched,
                  handleChange,
                  handleBlur,
                  handleSubmit,
                  isSubmitting,
                }) => (
                  <Form onSubmit={handleSubmit}>
                    <Form.Group controlId="formUsername">
                      <Form.Label className="text-success">Username</Form.Label>
                      <Form.Control
                        type="text"
                        name="username"
                        placeholder="Enter username"
                        value={values.username}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        isInvalid={touched.username && !!errors.username}
                      />
                      <Form.Control.Feedback type="invalid">
                        {errors.username}
                      </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group controlId="formPassword">
                      <Form.Label className="text-success">Password</Form.Label>
                      <Form.Control
                        type="password"
                        name="password"
                        placeholder="Enter password"
                        value={values.password}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        isInvalid={touched.password && !!errors.password}
                      />
                      <Form.Control.Feedback type="invalid">
                        {errors.password}
                      </Form.Control.Feedback>
                    </Form.Group>

                    <Button variant="success" type="submit" disabled={isSubmitting}>
                      Login
                    </Button>
                  </Form>
                )}
              </Formik>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}

export default Login;

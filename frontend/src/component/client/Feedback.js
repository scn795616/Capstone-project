import React, { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Formik } from 'formik';
import * as Yup from 'yup';
import api from '../../services/api';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheckCircle } from '@fortawesome/free-solid-svg-icons';
import { Form, Button, Container, Row, Col, Card, Alert } from 'react-bootstrap';

function Feedback() {
  const { serviceId } = useParams();
  const navigate = useNavigate();
  const [errorMessage, setErrorMessage] = useState('');
  const [success, setSuccess] = useState('');

  const initialValues = {
    feedback: '',
    rating: '',
  };

  const validationSchema = Yup.object({
    feedback: Yup.string()
      .required('Feedback is required')
      .min(10, 'Feedback must be at least 10 characters long'),
    rating: Yup.number()
      .required('Rating is required')
      .min(1, 'Rating must be at least 1')
      .max(5, 'Rating must be at most 5'),
  });

  const handleFeedbackSubmit = async (values, { setSubmitting }) => {
    try {
      // Assuming you have an API endpoint to submit feedback
      const response = await api.post('/feedback/create/'+serviceId, values);
      setSuccess(<><FontAwesomeIcon icon={faCheckCircle} /> Feedback submitted successfully</>);
      setTimeout(() => {
        setSuccess('');
        navigate('/home'); // Redirect to home or appropriate page after submission
      }, 2000); // Delay for 2 seconds
    } catch (error) {
      setErrorMessage('Failed to submit feedback. Please try again.');
      setTimeout(() => {
        setErrorMessage('');
      }, 2000); // Delay for 2 seconds
      console.error('Feedback submission failed:', error);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <Container style={{marginTop:'60px'}}>
      <Row className="justify-content-md-center">
        <Col md={6}>
          <Card className="feedback-card">
            <Card.Body>
              <h2 className="feedback-header text-center">Feedback</h2>
              {errorMessage && <div className="message error-message">{errorMessage}</div>}
              {success && <div className="message success-message">{success}</div>}
              <Formik
                initialValues={initialValues}
                validationSchema={validationSchema}
                onSubmit={handleFeedbackSubmit}
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
                    <Form.Group controlId="formFeedback">
                      <Form.Label className="text-success">Feedback</Form.Label>
                      <Form.Control
                        as="textarea"
                        rows={3}
                        name="feedback"
                        placeholder="Enter your feedback"
                        value={values.feedback}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        isInvalid={touched.feedback && !!errors.feedback}
                      />
                      <Form.Control.Feedback type="invalid">
                        {errors.feedback}
                      </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group controlId="formRating">
                      <Form.Label className="text-success">Rating</Form.Label>
                      <Form.Control
                        as="select"
                        name="rating"
                        value={values.rating}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        isInvalid={touched.rating && !!errors.rating}
                      >
                        <option value="">Select rating</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                      </Form.Control>
                      <Form.Control.Feedback type="invalid">
                        {errors.rating}
                      </Form.Control.Feedback>
                    </Form.Group>

                    <Button variant="success" type="submit" disabled={isSubmitting}>
                      Submit Feedback
                    </Button>
                  </Form>
                )}
              </Formik>
            </Card.Body>
          </Card>
        </Col>
      </Row>
      <style>
        {`
        .feedback-card {
        border-radius: 15px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        padding: 20px;
        background-color: #ffffff; /* White background */
        }

        .feedback-header {
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

        .message {
        margin-bottom: 20px;
        padding: 10px;
        border-radius: 5px;
        text-align: center;
        }

        .error-message {
        background-color: #f8d7da;
        color: #721c24;
        }

        .success-message {
        background-color: #d4edda;
        color: #155724;
        }

        `}
      </style>
    </Container>
  );
}

export default Feedback;

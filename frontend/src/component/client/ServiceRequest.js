import React, { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Formik } from 'formik';
import * as Yup from 'yup';
import { Form, Button, Container, Row, Col, Card, Alert } from 'react-bootstrap';
import api from "../../services/api";

const ServiceRequest = () => {
  const { categoryId } = useParams();
  console.log(categoryId);
  const [errorMessage, setErrorMessage] = useState('');
  const user = JSON.parse(sessionStorage.getItem('userDetails'));
  console.log(user);
  const navigate = useNavigate();
  if(user==null || user.role!="Client"){
    navigate('/login');
  }

  const initialValues = {
    serviceDescription: '',
    price: '',
  };

  const validationSchema = Yup.object({
    serviceDescription: Yup.string()
      .required('Service description is required')
      .min(10, 'Service description must be at least 10 characters long'),
    price: Yup.number()
      .required('Price is required')
      .positive('Price must be a positive number'),
  });

  const handleServiceRequest = async (values, { setSubmitting }) => {
    try {
      const response = await api.post(`/service-requests/create/`+categoryId+'/'+user.id, values);
      console.log('Service request submitted successfully:', response.data);
      navigate('/'); // Redirect to home or appropriate page after submission
    } catch (error) {
      setErrorMessage('Error while submitting the service request.');
      console.error('Error while submitting the service request:', error);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <Container>
      <Row className="justify-content-md-center">
        <Col md={6}>
          <Card className="service-request-card">
            <Card.Body>
              <h2 className="service-request-header text-center">Service Request</h2>
              {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
              <Formik
                initialValues={initialValues}
                validationSchema={validationSchema}
                onSubmit={handleServiceRequest}
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
                    <Form.Group controlId="formServiceDescription">
                      <Form.Label className="text-success">Service Description</Form.Label>
                      <Form.Control
                        as="textarea"
                        rows={3}
                        name="serviceDescription"
                        placeholder="Enter service description"
                        value={values.serviceDescription}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        isInvalid={touched.serviceDescription && !!errors.serviceDescription}
                      />
                      <Form.Control.Feedback type="invalid">
                        {errors.serviceDescription}
                      </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group controlId="formServicePrice">
                      <Form.Label className="text-success">Budget</Form.Label>
                      <Form.Control
                        type="number"
                        name="price"
                        placeholder="Enter price"
                        value={values.price}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        isInvalid={touched.price && !!errors.price}
                      />
                      <Form.Control.Feedback type="invalid">
                        {errors.price}
                      </Form.Control.Feedback>
                    </Form.Group>

                    <Button variant="success" type="submit" disabled={isSubmitting}>
                      Submit Request
                    </Button>
                  </Form>
                )}
              </Formik>
            </Card.Body>
          </Card>
        </Col>
      </Row>
      <style jsx>{`
        .service-request-card {
          border-radius: 15px;
          box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
          padding: 20px;
          background-color: #ffffff; /* White background */
        }

        .service-request-header {
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

export default ServiceRequest;

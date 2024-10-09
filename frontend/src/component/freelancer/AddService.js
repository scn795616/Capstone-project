import React, { useEffect, useState } from 'react';
import { useNavigate ,useParams} from 'react-router-dom';
import { Card, Form, Button, Container, Row, Col } from 'react-bootstrap';
import { Formik } from 'formik';
import * as Yup from 'yup';
import api from "../../services/api";
import './AddService.css'; // Import the custom CSS file

const AddService = () => {
  const navigate = useNavigate();
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await api.get('/service-categories/list');
        setCategories(response.data);
      } catch (error) {
        console.error('Error fetching categories:', error);
      }
    };

    fetchCategories();
  }, []);

  const initialValues = {
    name: '',
    description: '',
    price: '',
    category: '',
    image: null,
  };

  const validationSchema = Yup.object({
    name: Yup.string()
      .required('Service name is required')
      .min(3, 'Service name must be at least 3 characters long'),
    description: Yup.string()
      .required('Service description is required')
      .min(10, 'Service description must be at least 10 characters long'),
    price: Yup.number()
      .required('Price is required')
      .positive('Price must be a positive number'),
    category: Yup.string()
      .required('Category is required'),
    image: Yup.mixed().required('An image is required'),
  });

  const handleAddService = async (values, { setSubmitting, resetForm }) => {
    const formData = new FormData();
    formData.append('service', new Blob([JSON.stringify({
      name: values.name,
      description: values.description,
      price: values.price,
      category: values.category,
    })], { type: 'application/json' }));
    formData.append('image', values.image);

    try {
      const user = JSON.parse(sessionStorage.getItem('userDetails'));
      console.log(user.id);
      const response = await api.post('/services/create/' + user.id, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      console.log('Service added successfully:', response.data);
      navigate('/viewservice');
    } catch (error) {
      console.error('Error adding service:', error);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <Container>
      <Row className="justify-content-md-center">
        <Col md={6}>
          <Card className="add-service-card">
            <Card.Body>
              <Card.Title className="add-service-header text-center">Add Service</Card.Title>
              <Formik
                initialValues={initialValues}
                validationSchema={validationSchema}
                onSubmit={handleAddService}
              >
                {({
                  values,
                  errors,
                  touched,
                  handleChange,
                  handleBlur,
                  handleSubmit,
                  setFieldValue,
                  isSubmitting,
                }) => (
                  <Form onSubmit={handleSubmit}>
                    <Form.Group controlId="formServiceName">
                      <Form.Label className="text-success">Service Name</Form.Label>
                      <Form.Control
                        type="text"
                        name="name"
                        placeholder="Enter service name"
                        value={values.name}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        isInvalid={touched.name && !!errors.name}
                      />
                      <Form.Control.Feedback type="invalid">
                        {errors.name}
                      </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group controlId="formServiceDescription">
                      <Form.Label className="text-success">Service Description</Form.Label>
                      <Form.Control
                        as="textarea"
                        rows={3}
                        name="description"
                        placeholder="Enter service description"
                        value={values.description}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        isInvalid={touched.description && !!errors.description}
                      />
                      <Form.Control.Feedback type="invalid">
                        {errors.description}
                      </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group controlId="formServicePrice">
                      <Form.Label className="text-success">Price</Form.Label>
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

                    <Form.Group controlId="formServiceCategory">
                      <Form.Label className="text-success">Category</Form.Label>
                      <Form.Control
                        as="select"
                        name="category"
                        value={values.category}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        isInvalid={touched.category && !!errors.category}
                      >
                        <option value="">Select a category</option>
                        {categories.map(category => (
                          <option key={category.name} value={category.id}>
                            {category.name}
                          </option>
                        ))}
                      </Form.Control>
                      <Form.Control.Feedback type="invalid">
                        {errors.category}
                      </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group controlId="formServiceImage">
                      <Form.Label className="text-success">Service Image</Form.Label>
                      <Form.Control
                        type="file"
                        name="image"
                        onChange={(event) => {
                          setFieldValue('image', event.currentTarget.files[0]);
                        }}
                        onBlur={handleBlur}
                        isInvalid={touched.image && !!errors.image}
                      />
                      <Form.Control.Feedback type="invalid">
                        {errors.image}
                      </Form.Control.Feedback>
                    </Form.Group>

                    <Button variant="success" type="submit" disabled={isSubmitting}>
                      Add Service
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
};

export default AddService;

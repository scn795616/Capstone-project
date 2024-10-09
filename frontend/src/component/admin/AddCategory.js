import React from 'react';
import api from "../../services/api";
import { useNavigate } from 'react-router-dom';
import { Card, Form, Button, Container, Row, Col } from 'react-bootstrap';
import { Formik } from 'formik';
import * as Yup from 'yup';

const AddCategory = () => {
  const navigate = useNavigate();
  const initialValues = {
    name: '',
    description: '',
    image: null, // Add image field
  };

  const validationSchema = Yup.object({
    name: Yup.string()
      .required('Category name is required')
      .min(3, 'Category name must be at least 3 characters long'),
    description: Yup.string()
      .required('Category description is required')
      .min(10, 'Category description must be at least 10 characters long'),
    image: Yup.mixed().required('An image is required'), // Add validation for image
  });

  const handleAddCategory = async (values, { setSubmitting, resetForm }) => {
    const formData = new FormData();
    formData.append('serviceCategory', new Blob([JSON.stringify({
      name: values.name,
      description: values.description,
    })], { type: 'application/json' }));
    formData.append('image', values.image); // Append image to form data

    try {
      const response = await api.post('/service-categories/create', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });

      console.log('Category added successfully:', response.data);
      resetForm(); // Clear form fields after successful submission
      // Handle successful category addition here.
      // Uncomment if you'd like to navigate to a success page or another route after submission.
      navigate('/home'); 
    } catch (error) {
      console.error('Error adding category:', error);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <Container>
      <Row className="justify-content-md-center">
        <Col md={6}>
          <Card className="add-category-card">
            <Card.Body>
              <h2 className="add-category-header text-center">Add Category</h2>
              <Formik
                initialValues={initialValues}
                validationSchema={validationSchema}
                onSubmit={handleAddCategory}
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
                  <Form onSubmit={(e) => { 
                    e.preventDefault(); // Prevent form's default submission behavior
                    handleSubmit(); // Call Formik's handleSubmit function
                  }}>
                    <Form.Group controlId="formCategoryName">
                      <Form.Label className="text-success">Category Name</Form.Label>
                      <Form.Control
                        type="text"
                        name="name"
                        placeholder="Enter category name"
                        value={values.name}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        isInvalid={touched.name && !!errors.name}
                      />
                      <Form.Control.Feedback type="invalid">
                        {errors.name}
                      </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group controlId="formCategoryDescription">
                      <Form.Label className="text-success">Category Description</Form.Label>
                      <Form.Control
                        as="textarea"
                        rows={3}
                        name="description"
                        placeholder="Enter category description"
                        value={values.description}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        isInvalid={touched.description && !!errors.description}
                      />
                      <Form.Control.Feedback type="invalid">
                        {errors.description}
                      </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group controlId="formCategoryImage">
                      <Form.Label className="text-success">Category Image</Form.Label>
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

                    <Button
                      variant="success"
                      type="submit"
                      disabled={isSubmitting}
                    >
                      Add Category
                    </Button>
                  </Form>
                )}
              </Formik>
            </Card.Body>
          </Card>
        </Col>
      </Row>
      <style>{`
        .add-category-card {
          border-radius: 15px;
          box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
          margin-top: 20px;
        }

        .add-category-header {
          background-color: green; /* Green background */
          color: #ffffff; /* White text */
          padding: 10px;
          border-radius: 15px 15px 0 0; /* Rounded corners at the top */
        }

        .text-success {
          color: green; /* Green text */
        }

        .btn-success {
          background-color: green; /* Green button */
          border-color: green; /* Green border */
        }
      `}</style>
    </Container>
  );
};

export default AddCategory;

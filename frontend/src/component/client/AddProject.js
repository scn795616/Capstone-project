import React, { useEffect, useState } from 'react';
import { useNavigate ,useParams} from 'react-router-dom';
import { Card, Form, Button, Container, Row, Col } from 'react-bootstrap';
import { Formik } from 'formik';
import * as Yup from 'yup';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheckCircle } from '@fortawesome/free-solid-svg-icons';
import api from "../../services/api";

const AddProject = () => {
  const navigate = useNavigate();
  const [categories, setCategories] = useState([]);
  const [success, setSuccess] = useState('');

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
      .required('Project name is required')
      .min(3, 'Project name must be at least 3 characters long'),
    description: Yup.string()
      .required('Project description is required')
      .min(10, 'Project description must be at least 10 characters long'),
    price: Yup.number()
      .required('Price is required')
      .positive('Price must be a positive number'),
    category: Yup.string()
      .required('Category is required'),
    image: Yup.mixed().required('An description file is required')
    .test('fileType', 'Only PDF files are allowed', value => value && value.type === 'application/pdf')
    .test('fileSize', 'File size must be less than 5MB', value => value && value.size <= 5 * 1024 * 1024),
    
  });

  const handleAddService = async (values, { setSubmitting, resetForm }) => {
    const formData = new FormData();
    formData.append('project', new Blob([JSON.stringify({
      name: values.name,
      description: values.description,
      budget: values.price,
      category: values.category,
    })], { type: 'application/json' }));
    formData.append('image', values.image);

    try {
      const user = JSON.parse(sessionStorage.getItem('userDetails'));
      console.log(user.id);
      const response = await api.post('/projects/create/' + user.id, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      console.log('Project added successfully:', response.data);
      resetForm();
      navigate('/viewprojects');
      setSuccess(<><FontAwesomeIcon icon={faCheckCircle} /> Added Successfully</>);
     
      // setTimeout(() => {
      //   setSuccess('');
      //   navigate('/viewprojects'); // Redirect to home or appropriate dashboard
      // }, 2000); // Delay for 2 seconds
    } catch (error) {
      console.error('Error adding project:', error);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <Container>
      <Row className="justify-content-md-center">
        <Col md={6}>
        {success && <div className="message success-message">{success}</div>}
          <Card className="add-service-card">
            <Card.Body>
              <Card.Title className="add-service-header text-center">Add Project</Card.Title>
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
                  <Form onSubmit={(e) => { 
                    e.preventDefault(); // Prevent form's default submission behavior
                    handleSubmit(); // Call Formik's handleSubmit function
                  }}>
                    <Form.Group controlId="formServiceName">
                      <Form.Label className="text-success">Project Name</Form.Label>
                      <Form.Control
                        type="text"
                        name="name"
                        placeholder="Enter service name"
                        value={values.name}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        isInvalid={touched.name && !!errors.name}
                        isValid={touched.name && !errors.name}
                      />
                      <Form.Control.Feedback type="invalid">
                        {errors.name}
                      </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group controlId="formServiceDescription">
                      <Form.Label className="text-success">Project Description</Form.Label>
                      <Form.Control
                        as="textarea"
                        rows={3}
                        name="description"
                        placeholder="Enter service description"
                        value={values.description}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        isInvalid={touched.description && !!errors.description}
                        isValid={touched.description && !errors.description}
                      />
                      <Form.Control.Feedback type="invalid">
                        {errors.description}
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
                        isValid={touched.price && !errors.price}
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
                        isValid={touched.category && !errors.category}
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
                      <Form.Label className="text-success">Project Description File</Form.Label>
                      <Form.Control
                        type="file"
                        name="image"
                        onChange={(event) => {
                          setFieldValue('image', event.currentTarget.files[0]);
                        }}
                        onBlur={handleBlur}
                        isInvalid={touched.image && !!errors.image}
                        isValid={touched.image && !errors.image}
                      />
                      <Form.Control.Feedback type="invalid">
                        {errors.image}
                      </Form.Control.Feedback>
                    </Form.Group>

                    <Button variant="success" type="submit" disabled={isSubmitting}>
                      Add Project
                    </Button>
                  </Form>
                )}
              </Formik>
            </Card.Body>
          </Card>
        </Col>
      </Row>
      <style jsx>{`
      /* Custom styles for the add service card */
.add-service-card {
    border-radius: 15px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    padding: 20px;
    background-color: #ffffff; /* White background */
  }
  
  .add-service-header {
    background-color: green; /* Green background */
    color: #ffffff; /* White text */
    padding: 10px;
    border-radius: 15px 15px 0 0; /* Rounded corners at the top */
    margin: -20px -20px 20px -20px; /* Full width */
  }
  
  .text-success {
    color: #28a745 !important;
    font-weight: bold; /* Green color for labels */
  }
  
  .button.btn-success {
    width: 100%;
    margin-top: 20px;
  }
.valid-feedback-icon {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  color: green;
}

.is-valid {
  border-color: green !important;
}
.message {
  position: relative;
  padding: 10px;
  margin-bottom: 20px;
}

.success-message {
  background-color: #d4edda;
  border: 1px solid #c3e6cb;
  color: #155724;
}

.error-message {
  background-color: #f8d7da;
  border: 1px solid #f5c6cb;
  color: #721c24;
}

.message::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 5px;
}

.success-message::after {
  background-color: green;
  animation: shrink 2s forwards;
}

.error-message::after {
  background-color: rgba(237, 50, 53, 0.82);
  animation: shrink 2s forwards;
}

@keyframes shrink {
  from {
    width: 100%;
  }
  to {
    width: 0;
  }
}

  
      `}

      </style>
    </Container>
  );
};

export default AddProject;

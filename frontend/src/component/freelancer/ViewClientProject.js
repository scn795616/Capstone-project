import React, { useState, useEffect } from 'react';
import api from "../../services/api";
import { Container, Alert, Card, Table, Button, Modal, Form } from 'react-bootstrap';
import { Formik } from 'formik';
import * as Yup from 'yup';

const ViewClientProject = () => {
  const [categories, setCategories] = useState([]);
  const [error, setError] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [selectedCategoryId, setSelectedCategoryId] = useState(null);
  const [showResponseModal, setShowResponseModal] = useState(false);
  const [responses, setResponses] = useState([]);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const user = JSON.parse(sessionStorage.getItem('userDetails'));
        console.log(user.id);

        const response = await api.get('http://localhost:8989/api/projects/list', {
          headers: {
            'Content-Type': 'application/json',
          },
        });
        setCategories(response.data);
        console.log(categories.id);
      } catch (error) {
        setError('Error while fetching the data');
      }
    };

    fetchCategories();
  }, []);

  const handleAddProposal = (id) => {
    setSelectedCategoryId(id);
    setShowModal(true);
  };

  const handleCloseRequest = (id) => {
    // Implement close request logic here
    console.log('Close Request for ID:', id);
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setSelectedCategoryId(null);
  };

  const handleSubmitProposal = async (values, { setSubmitting }) => {
    try {
        console.log("hi"+ selectedCategoryId);
        const user = JSON.parse(sessionStorage.getItem('userDetails'));
      const response = await api.post(`/proposals/createbyproject/` + selectedCategoryId + '/' +user.id, values);
      console.log('Proposal submitted successfully:', response.data);
      handleCloseModal();
    } catch (error) {
      console.error('Error submitting proposal:', error);
    } finally {
      setSubmitting(false);
    }
  };

  const handleViewResponse = async (id) => {
    try {
        console.log(id);
        const user = JSON.parse(sessionStorage.getItem('userDetails'));
      const response = await api.get(`/proposals/listprojects/`+id+'/'+user.id);
      setResponses(response.data);
      setShowResponseModal(true);
    } catch (error) {
      console.error('Error fetching responses:', error);
    }
  };

  const handleCloseResponseModal = () => {
    setShowResponseModal(false);
    setResponses([]);
  };

  const initialValues = {
    proposal: '',
    price: '',
    estimatedTime: '',
  };

  const validationSchema = Yup.object({
    proposal: Yup.string().required('Proposal is required'),
    price: Yup.number().required('Price is required').positive('Price must be positive'),
    estimatedTime: Yup.number().required('Estimated time is required').positive('Estimated time must be positive'),
  });

  const downloadFile = (fileUrl) => {
    const link = document.createElement('a');
    link.href = fileUrl;
    link.download = fileUrl.split('/').pop();
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  };

  return (
    <Container>
      <Card className="service-request-card">
        <Card.Header className="service-request-header text-center">
          <h2>Project Requests</h2>
        </Card.Header>
        <Card.Body>
          {error && <Alert variant="danger">{error}</Alert>}
          <Table striped bordered hover>
            <thead className="table-header">
              <tr>
              <th style={{backgroundColor:'green'}}>Image</th>
                <th style={{backgroundColor:'green'}}>Description</th>
                <th style={{backgroundColor:'green'}}>Budget</th>
                <th style={{backgroundColor:'green'}}>Category Name</th>
                <th style={{backgroundColor:'green'}}>File</th>
                <th style={{backgroundColor:'green'}}>Status</th>
                <th style={{backgroundColor:'green'}}>Action</th>
              </tr>
            </thead>
            <tbody>
              {categories.map((category) => (
                <tr key={category.id} style={{color:'green'}}>
                  <td><img src={category.serviceCategory.imageUrl} alt={category.name} style={{ width: '100px', height: '70px' }} /></td>
                  <td style={{color:'green',fontWeight:'bold'}}>{category.description}</td>
                  <td style={{color:'green',fontWeight:'bold'}}>{category.budget}</td>
                  <td style={{color:'green',fontWeight:'bold'}}>{category.serviceCategory.name}</td>
                  <td> 
                    <div className='action-buttons'>
                    <Button variant="success" onClick={() => downloadFile(category.imageUrl)} >Download</Button>

                    </div>
                    </td>

                  <td style={{color:'green',fontWeight:'bold'}}>{category.status}</td>
                  <td>
                  {category.status==='Open' && (
                      <div className="action-buttons">
                      <Button variant="success" onClick={() => handleAddProposal(category.id)}>Add Proposal</Button>
                      <Button variant="danger" onClick={() => handleCloseRequest(category.id)} style={{ marginLeft: '10px' }}>Close</Button>
                    </div>
                    )}
                    <div>
                        
                      <Button variant="success" onClick={() => handleViewResponse(category.id)}>Response</Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </Card.Body>
      </Card>

      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton className="modal-header">
          <Modal.Title>Add Proposal</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Formik
            initialValues={initialValues}
            validationSchema={validationSchema}
            onSubmit={handleSubmitProposal}
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
                <Form.Group controlId="formProposal">
                  <Form.Label className="text-success">Proposal</Form.Label>
                  <Form.Control
                    type="text"
                    name="proposal"
                    placeholder="Enter proposal"
                    value={values.proposal}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    isInvalid={touched.proposal && !!errors.proposal}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.proposal}
                  </Form.Control.Feedback>
                </Form.Group>

                <Form.Group controlId="formPrice">
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

                <Form.Group controlId="formEstimatedTime">
                  <Form.Label className="text-success">Estimated Time (in days)</Form.Label>
                  <Form.Control
                    type="number"
                    name="estimatedTime"
                    placeholder="Enter estimated time"
                    value={values.estimatedTime}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    isInvalid={touched.estimatedTime && !!errors.estimatedTime}
                  />
                  <Form.Control.Feedback type="invalid">
                    {errors.estimatedTime}
                  </Form.Control.Feedback>
                </Form.Group>

                <Button variant="success" type="submit" disabled={isSubmitting}>
                  Submit Proposal
                </Button>
              </Form>
            )}
          </Formik>
        </Modal.Body>
      </Modal>

      <Modal show={showResponseModal} onHide={handleCloseResponseModal} dialogClassName="modal-90w">        
      <Modal.Header closeButton className="modal-header">
        <Modal.Title className='text-center'>View Responses</Modal.Title>
    </Modal.Header>
        <Modal.Body>
        <Card className="service-request-card">
        {/* <Card.Header className="service-request-header text-center">
          <h2>Service Requests</h2>
        </Card.Header> */}
        <Card.Body>
          {error && <Alert variant="danger">{error}</Alert>}
          <Table striped bordered hover>
            <thead className="table-header">
              <tr>
                <th style={{backgroundColor:'green'}}>Description</th>
                <th style={{backgroundColor:'green'}}>Price</th>
                <th style={{backgroundColor:'green'}}>Customer Name</th>
                <th style={{backgroundColor:'green'}}>Message</th>
                <th style={{backgroundColor:'green'}}>Status</th>
              </tr>
            </thead>
            <tbody>
              {responses.map((response) => (
                <tr key={response.id} style={{color:'green'}}>
                  <td style={{color:'green',fontWeight:'bold'}}>{response.proposal}</td>
                  <td style={{color:'green',fontWeight:'bold'}}>{response.price}</td>
                  <td style={{color:'green',fontWeight:'bold'}}>{response.client.firstName}</td>
                  <td style={{color:'green',fontWeight:'bold'}}>{response.customerMessage}</td>

                  <td style={{color:'green',fontWeight:'bold'}}>{response.status}</td>
                  
                </tr>
              ))}
            </tbody>
          </Table>
        </Card.Body>
      </Card>
        </Modal.Body>
      </Modal>

      <style jsx>{`
      .modal-90w {
        max-width: 90%;
        }   
        .service-request-card {
          border-radius: 15px;
          box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
          margin-top: 20px;
        }

        .service-request-header {
          background-color: green; /* Green background */
          color: #ffffff; /* White text */
          padding: 10px;
          border-radius: 15px 15px 0 0; /* Rounded corners at the top */
        }

        .table-header {
          background-color: #28a745; /* Success color */
          color: #ffffff; /* White text */
        }

        .table-row {
          color: green; /* Green color for row data */
        }

        .table-row:hover {
          color: black; /* Black color on hover */
        }

        .action-buttons {
          display: flex;
          align-items: center;
        }

        .action-buttons button {
          margin: 0 5px;
        }

        .text-success {
          color: green; /* Green text */
        }

        .btn-success {
          background-color: green; /* Green button */
          border-color: green; /* Green border */
        }

        .modal-header {
          background-color: green; /* Green background */
          color: #ffffff; /* White text */
          width: 100%;
        }
      `}</style>
    </Container>
  );
};

export default ViewClientProject;

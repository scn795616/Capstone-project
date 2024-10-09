import React, { useState, useEffect} from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import api from '../../services/api';
import { Container, Alert, Card, Table, Button, Modal, Form } from 'react-bootstrap';
import { Formik, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';

const ViewServiceRequest = () => {
  const [categories, setCategories] = useState([]);
  const navigate = useNavigate();
  const [error, setError] = useState('');
  const [showResponseModal, setShowResponseModal] = useState(false);
  const [responses, setResponses] = useState([]);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const user = JSON.parse(sessionStorage.getItem('userDetails'));
  console.log(user);
  if(user==null || user.role!="Client"){
    navigate('/login');
  }
  useEffect(() => {
    const fetchCategories = async () => {
      try {
        console.log(user.id);
        const response = await api.get(`http://localhost:8989/api/service-requests/list/${user.id}`, {
          headers: { 'Content-Type': 'application/json' },
        });
        setCategories(response.data);
      } catch (error) {
        setError('Error while fetching the data');
      }
    };
    fetchCategories();
  }, []);

  const handleViewResponse = async (id) => {
    try {
      const response = await api.get(`/proposals/list/${id}`);
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

  const handleCloseRequest = (id) => {
    // Implement close request logic here
    console.log('Close Request for ID:', id);
    };

    const handleUpdateAction = async (id,serviceId ,values) => {
      setIsSubmitting(true);
      try {
        console.log(values);
        const response = await api.put(`/proposals/update/${id}`, values);
        console.log('Action updated successfully:', response.data);
        setShowResponseModal(false);
        setResponses([]);
        navigate('/home');
        // navigate(`/feedback/${serviceId}`);
        // Optionally, you can refresh the responses or close the modal here
      } catch (error) {
        console.error('Error updating action:', error);
      } finally {
        setIsSubmitting(false);
      }
    };

  

  return (
    <Container>
      <Card className="service-request-card">
        <Card.Header className="service-request-header text-center">
          <h2>Service Requests</h2>
        </Card.Header>
        <Card.Body>
          {error && <Alert variant="danger">{error}</Alert>}
          <Table striped bordered hover>
            <thead className="table-header">
              <tr>
                <th style={{backgroundColor:'green'}}>Image</th>
                <th style={{backgroundColor:'green'}}>Description</th>
                <th style={{backgroundColor:'green'}}>Price</th>
                <th style={{backgroundColor:'green'}}>Service Name</th>
                <th style={{backgroundColor:'green'}}>Status</th>
                <th style={{backgroundColor:'green'}}>Action</th>
              </tr>
            </thead>
            <tbody>
              {categories.map((category) => (
                <tr key={category.id}>
                  <td><img src={category.service.imageUrl} alt={category.name} style={{ width: '100px', height: '70px' }} /></td>
                  <td style={{color:'green',fontWeight:'bold'}}>{category.serviceDescription}</td>
                  <td style={{color:'green',fontWeight:'bold'}}>{category.price}</td>
                  <td style={{color:'green',fontWeight:'bold'}}>{category.service.name}</td>
                  <td style={{color:'green',fontWeight:'bold'}}>{category.status}</td>
                  <td>
                    <div className="action-buttons">
                      <Button variant="success" onClick={() => handleViewResponse(category.id)}>View Proposal</Button>
                      {category.status === 'pending' && (
                      <Button variant="danger" onClick={() => handleCloseRequest(category.id)} style={{ marginLeft: '10px' }}>Close</Button>

                      )}
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </Card.Body>
      </Card>
      <Modal show={showResponseModal} onHide={handleCloseResponseModal} dialogClassName="modal-90w">
        <Modal.Header closeButton className="modal-header">
          <Modal.Title className='text-center'>View Responses</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Card className="service-request-card">
            <Card.Body>
              {error && <Alert variant="danger">{error}</Alert>}
              <Table striped bordered hover>
                <thead className="table-header">
                  <tr>
                    <th style={{backgroundColor:'green'}}>Description</th>
                    <th style={{backgroundColor:'green'}}>Price</th>
                    <th style={{backgroundColor:'green'}}>Estimated days</th>
                    <th style={{backgroundColor:'green'}}>Freelancer Name</th>
                    <th style={{backgroundColor:'green'}}>Message</th>
                    <th style={{backgroundColor:'green'}}>Status</th>
                    <th style={{backgroundColor:'green'}}>Action</th>
                  </tr>
                </thead>
                <tbody>
                  {responses.map((response) => (
                    <tr key={response.id}>
                      <td style={{color:'green',fontWeight:'bold'}}>{response.proposal}</td>
                      <td style={{color:'green',fontWeight:'bold'}}>{response.price}</td>
                      <td style={{color:'green',fontWeight:'bold'}}>{response.estimatedTime}</td>
                      <td style={{color:'green',fontWeight:'bold'}}>{response.freelancer.firstName}</td>
                      <td style={{color:'green',fontWeight:'bold'}}>{response.customerMessage}</td>
                      <td style={{color:'green',fontWeight:'bold'}}>{response.status}</td>
                      {response.status === 'pending' && (
                        <td style={{color:'green',fontWeight:'bold'}}>
                            <Formik
                          initialValues={{ message: '', status: '' }}
                          validationSchema={Yup.object({
                            message: Yup.string().required('Message is required'),
                            status: Yup.string().required('Status is required'),
                          })}
                          onSubmit={(values, { setSubmitting }) => {
                            handleUpdateAction(response.id, response.serviceRequest.id,values);
                            setSubmitting(false);
                          }}
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
                              <Form.Group controlId={`formMessage-${response.id}`}>
                                <Form.Control
                                  type="text"
                                  name="message"
                                  placeholder="Enter message"
                                  value={values.message}
                                  onChange={handleChange}
                                  onBlur={handleBlur}
                                  isInvalid={touched.message && !!errors.message}
                                  style={{ marginBottom: '10px' }}
                                />
                                <Form.Control.Feedback type="invalid">
                                  {errors.message}
                                </Form.Control.Feedback>
                              </Form.Group>
                              <div style={{ display: 'flex', alignItems: 'center' }}>
                                <Form.Group controlId={`formStatus-${response.id}`} style={{ flex: 1 }}>
                                  <Form.Control
                                    as="select"
                                    name="status"
                                    value={values.status}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                    isInvalid={touched.status && !!errors.status}
                                  >
                                    <option value="">status</option>
                                    <option value="Approved">Approve</option>
                                    <option value="Denied">Deny</option>
                                  </Form.Control>
                                  <Form.Control.Feedback type="invalid">
                                    {errors.status}
                                  </Form.Control.Feedback>
                                </Form.Group>
                                <Button
                                  variant="success"
                                  type="submit"
                                  disabled={isSubmitting}
                                  style={{ marginLeft: '10px', width: '80px', marginBottom:'15px'}}
                                >
                                  Update
                                </Button>
                              </div>
                            </Form>
                          )}
                        </Formik>
                      </td>
                      )}
                      
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
          background-color: green;
          color: #ffffff;
          padding: 10px;
          border-radius: 15px 15px 0 0;
        }
        .table-header {
          background-color: #28a745;
          color: #ffffff;
        }
        .table-row {
          color: green;
        }
        .table-row:hover {
          color: black;
        }
        .action-buttons {
          display: flex;
          align-items: center;
        }
        .action-buttons button {
          margin: 0 5px;
        }
        .modal-header {
          background-color: green;
          color: #ffffff;
          width: 100%;
        }
      `}</style>
    </Container>
  );
};

export default ViewServiceRequest;

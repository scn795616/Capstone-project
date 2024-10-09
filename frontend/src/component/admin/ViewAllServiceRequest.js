import React, { useState, useEffect } from 'react';
import api from '../../services/api';
import { Container, Alert, Card, Table, Button, Modal, Form } from 'react-bootstrap';
import { Formik, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';

const ViewAllServiceRequest = () => {
  const [categories, setCategories] = useState([]);
  const [error, setError] = useState('');
  const [showResponseModal, setShowResponseModal] = useState(false);
  const [responses, setResponses] = useState([]);
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const user = JSON.parse(sessionStorage.getItem('userDetails'));
        const response = await api.get(`http://localhost:8989/api/service-requests/list`, {
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

    const handleUpdateAction = async (id, values) => {
      setIsSubmitting(true);
      try {
        console.log(values);
        const response = await api.put(`/proposals/update/${id}`, values);
        console.log('Action updated successfully:', response.data);
        setShowResponseModal(false);
        setResponses([]);
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
          <h2>All Service Requests</h2>
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
                  
                </tr>
              ))}
            </tbody>
          </Table>
        </Card.Body>
      </Card>
      
      <style>{`
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

export default ViewAllServiceRequest;

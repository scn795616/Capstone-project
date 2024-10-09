import React, { useState, useEffect } from 'react';
import api from "../../services/api";
import { Table, Container, Alert, Card } from 'react-bootstrap';

const ViewAllService = () => {
  const [categories, setCategories] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const user = JSON.parse(sessionStorage.getItem('userDetails'));

        const response = await api.get('http://localhost:8989/api/services/list', {
          headers: {
            'Content-Type': 'application/json',
          },
        });
        setCategories(response.data);
      } catch (error) {
        setError('Error while fetching the data');
      }
    };

    fetchCategories();
  }, []);

  const convertToBase64 = (byteArray) => {
    if (!byteArray) return '';
    const base64String = btoa(
      new Uint8Array(byteArray).reduce((data, byte) => data + String.fromCharCode(byte), '')
    );
    console.log(base64String); // Log the base64 string
    return `data:image/jpeg;base64,${base64String}`;
  };

  return (
    <Container>
      <Card className="categories-card">
        <Card.Header className="categories-header text-center">
          <h2>All Service List</h2>
        </Card.Header>
        <Card.Body>
          {error && <Alert variant="danger">{error}</Alert>}
          <Table striped bordered hover>
            <thead className="table-header">
              <tr>
                <th style={{backgroundColor:'green'}}>Name</th>
                <th style={{backgroundColor:'green'}}>Description</th>
                <th style={{backgroundColor:'green'}}>Min Price</th>
                <th style={{backgroundColor:'green'}}>Service Category</th>
                <th style={{backgroundColor:'green'}}>Image</th>
              </tr>
            </thead>
            <tbody>
              {categories.map((category) => (
                <tr key={category.id} style={{color:'green'}}>
                  <td style={{color:'green',fontWeight:'bold'}}>{category.name}</td>
                  <td style={{color:'green',fontWeight:'bold'}}>{category.description}</td>
                  <td style={{color:'green',fontWeight:'bold'}}>{category.price}</td>
                  <td style={{color:'green',fontWeight:'bold'}}>{category.serviceCategory.name}</td>
                  <td>
                    <img src={category.imageUrl} alt={category.name} style={{ width: '100px', height: '70px' }} />
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </Card.Body>
      </Card>
      <style>{`
        .categories-card {
          border-radius: 15px;
          box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
          margin-top: 20px;
        }

        .categories-header {
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
      `}</style>
    </Container>
  );
};

export default ViewAllService;
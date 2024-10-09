import React, { useState, useEffect } from 'react';
import api from "../services/api";
import { Container, Alert, Card, Row, Col, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import ChatComponent from './ChatComponent'; // Import the ChatComponent

const ViewCategories = () => {
  const [categories, setCategories] = useState([]);
  const [error, setError] = useState('');
  const navigate = useNavigate();

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

  const handleRequestService = (categoryId) => {
    // Navigate to the request service page with the selected category ID
    navigate(`/request-service/${categoryId}`);
  };

  const user = JSON.parse(sessionStorage.getItem('userDetails'));
  var userRole='';
  if (user!=null) {
    userRole = user.role;
  } else {
    userRole='';
  }
  

  return (
    <div className="page-container">
      <style jsx>{`
        .page-container {
          background-color: #f0f8ff; /* Light blue background */
          padding: 20px;
          border-radius: 10px;
        }

        .category-card {
          transition: transform 0.2s, box-shadow 0.2s;
          border: none;
          border-radius: 20px; /* Increased border radius */
          overflow: hidden;
          background-color: #ffffff; /* Light cyan background */
        }

        .category-card:hover {
          transform: scale(1.05);
          box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
        }

        .category-image {
          height: 100%;
          object-fit: fill;
          border-top-left-radius: 20px;
          border-bottom-left-radius: 20px;
        }

        .card-body {
          background-color: #ffffff; /* White background for the card body */
          border-top-right-radius: 20px;
          border-bottom-right-radius: 20px;
        }

        .text {
          color: #28a745; /* Green color for text */
        }
      `}</style>
      <Container>
        <h2>Services</h2>
        {error && <Alert variant="danger">{error}</Alert>}
        <Row>
          {categories.map((category) => (
            <Col key={category.id} sm={12} md={6} className="mb-4">
              <Card className="h-100 category-card">
                <Row className="no-gutters">
                  <Col md={4}>
                    <Card.Img variant="top" src={category.imageUrl} alt={category.name} className="category-image" />
                  </Col>
                  <Col md={8}>
                    <Card.Body>
                      <Card.Title className='text'>{category.name}</Card.Title>
                      <Card.Text>
                        {category.description}<br />
                        <strong>Min Price:</strong> <span className='text'>{category.price}</span><br />
                        <strong>Category:</strong> <span className='text'>{category.serviceCategory.name}</span>
                      </Card.Text>
                      {userRole === 'Client' && (
                        <Button variant="success" onClick={() => handleRequestService(category.id)}>
                          Request Service
                        </Button>
                      )}
                    </Card.Body>
                  </Col>
                </Row>
              </Card>
            </Col>
          ))}
        </Row>
      </Container>
    </div>
  );
};

export default ViewCategories;

import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Formik } from 'formik';
import * as Yup from 'yup';
import { Form, Button, Container, Row, Col, Card, Alert, Spinner } from 'react-bootstrap';
import api from "../../services/api";

const AddWallet = () => {
  const navigate = useNavigate();
  const [walletBalance, setWalletBalance] = useState(0);
  const [loading, setLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');

  useEffect(() => {
    const fetchWalletBalance = async () => {
      try {
        const user = JSON.parse(sessionStorage.getItem('userDetails'));
        const response = await api.get(`http://localhost:8989/api/wallets/list/${user.id}`);
        setWalletBalance(response.data.balance);
      } catch (error) {
        setErrorMessage('Error fetching wallet balance.');
        console.error('Error fetching wallet balance:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchWalletBalance();
  }, []);

  const initialValues = {
    amount: '',
  };

  const validationSchema = Yup.object({
    amount: Yup.number()
      .required('Amount is required')
      .positive('Amount must be a positive number'),
  });

  const handleAddAmount = async (values, { setSubmitting, resetForm }) => {
    setErrorMessage('');
    setSuccessMessage('');
    try {
      const user = JSON.parse(sessionStorage.getItem('userDetails'));
      const response = await api.put(`/wallets/add/${user.id}`, { amount: values.amount });
      console.log('Amount added successfully:', response.data);
      setSuccessMessage('Amount added successfully!');
      setWalletBalance(walletBalance + parseFloat(values.amount)); // Make sure amount is added as a float
      resetForm();
      setTimeout(() => {
        setSuccessMessage('');
        navigate('/'); // Redirect to home or appropriate page after submission
      }, 2000); // Delay for 2 seconds
    } catch (error) {
      setErrorMessage(error.response?.data?.message || 'Error while adding the amount.');
      console.error('Error while adding the amount:', error);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <Container>
      <Row className="justify-content-md-center">
        <Col md={6}>
          <Card className="wallet-card">
            <Card.Body>
              <h2 className="wallet-header text-center">Wallet Balance</h2>
              {loading ? (
                <div className="text-center">
                  <Spinner animation="border" variant="success" />
                </div>
              ) : (
                <Form.Group controlId="formWalletBalance">
                  <Form.Label className="text-success">Current Balance</Form.Label>
                  <Form.Control
                    type="text"
                    value={`₹${walletBalance.toFixed(2)}`} // Display as currency
                    readOnly
                  />
                </Form.Group>
              )}
            </Card.Body>
          </Card>

          <Card className="wallet-card mt-4">
            <Card.Body>
              <h2 className="wallet-header text-center">Add Amount</h2>
              {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
              {successMessage && <Alert variant="success">{successMessage}</Alert>}
              <Formik
                initialValues={initialValues}
                validationSchema={validationSchema}
                onSubmit={handleAddAmount}
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
                    <Form.Group controlId="formAddAmount">
                      <Form.Label className="text-success">Amount</Form.Label>
                      <Form.Control
                        type="number"
                        name="amount"
                        placeholder="Enter amount"
                        value={values.amount}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        isInvalid={touched.amount && !!errors.amount}
                      />
                      <Form.Control.Feedback type="invalid">
                        {errors.amount}
                      </Form.Control.Feedback>
                    </Form.Group>

                    <Button variant="success" type="submit" disabled={isSubmitting || loading}>
                      {isSubmitting ? 'Processing...' : 'Add Amount'}
                    </Button>
                  </Form>
                )}
              </Formik>
            </Card.Body>
          </Card>
        </Col>
      </Row>
      <style jsx>{`
        .wallet-card {
          border-radius: 15px;
          box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
          padding: 20px;
          background-color: #ffffff;
        }

        .wallet-header {
          background-color: green;
          color: #ffffff;
          padding: 10px;
          border-radius: 15px 15px 0 0;
          margin: -20px -20px 20px -20px;
        }

        .text-success {
          color: #28a745 !important;
        }

        button.btn-success {
          width: 100%;
          margin-top: 20px;
        }
      `}</style>
    </Container>
  );
};

export default AddWallet;

import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Navbar, Nav, NavDropdown, Container, Button } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHome, faInfoCircle, faEnvelope, faSignInAlt, faUserPlus, faSignOutAlt, faPlus, faList, faTasks, faUser, faWallet,faComment } from '@fortawesome/free-solid-svg-icons';
import './Navbar.css'; // Import the custom CSS file

import logo from '../../images/Designer (4).jpeg'
import SupportEngine from '../SupportEngine';

const CustomNavbar = ({ userRole, setUserRole }) => {
  const navigate = useNavigate();
  const handleLogout = () => {
    setUserRole('');
    sessionStorage.setItem('userDetails', null);
    navigate('/');
  };

  return (
    <><Navbar bg="success" variant="dark" expand="lg" className='App'>
      <Container>
        <Navbar.Brand as={Link} to="/">
          <img
            src={logo}
            width="40"
            height="30"
            className="d-inline-block align-top"
            alt="Gig Worker Logo" />
          Gig Worker Platform
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="ms-auto">
            <Nav.Link as={Link} to="/"><FontAwesomeIcon icon={faHome} /> Home</Nav.Link>
            <Nav.Link as={Link} to="/contact"><FontAwesomeIcon icon={faEnvelope} /> Contact Us</Nav.Link>
            {userRole === 'Admin' && (
              <>
                <NavDropdown title={<><FontAwesomeIcon icon={faTasks} /> Category</>} id="basic-nav-dropdown">
                  <NavDropdown.Item as={Link} to="/addcategory"><FontAwesomeIcon icon={faPlus} /> Add Category</NavDropdown.Item>
                  <NavDropdown.Item as={Link} to="/viewcategory"><FontAwesomeIcon icon={faList} /> View Category</NavDropdown.Item>
                </NavDropdown>
                <NavDropdown title={<><FontAwesomeIcon icon={faTasks} /> Service</>} id="basic-nav-dropdown">
                  <NavDropdown.Item as={Link} to="/allservice"><FontAwesomeIcon icon={faList} /> All Services</NavDropdown.Item>
                </NavDropdown>
                <NavDropdown title={<><FontAwesomeIcon icon={faTasks} /> Service Request</>} id="basic-nav-dropdown">
                  <NavDropdown.Item as={Link} to="/allservicerequest"><FontAwesomeIcon icon={faList} /> Service Request</NavDropdown.Item>
                </NavDropdown>
                <NavDropdown title={<><FontAwesomeIcon icon={faTasks} /> Manage</>} id="basic-nav-dropdown">
                  
                  <NavDropdown.Item as={Link} to="/register-admin"><FontAwesomeIcon icon={faUserPlus} /> Register Admin</NavDropdown.Item>
                  <NavDropdown.Item as={Link} to="/support"><FontAwesomeIcon icon={faComment} /> Chat</NavDropdown.Item>

                </NavDropdown>
              </>
            )}
            {userRole === 'Client' && (
              <>
                <NavDropdown title={<><FontAwesomeIcon icon={faTasks} /> Service</>} id="basic-nav-dropdown">
                  <NavDropdown.Item as={Link} to="/viewservice-request"><FontAwesomeIcon icon={faTasks} /> Service Request</NavDropdown.Item>
                  <NavDropdown.Item as={Link} to="/addproject"><FontAwesomeIcon icon={faPlus} /> Add Project</NavDropdown.Item>
                  <NavDropdown.Item as={Link} to="/viewprojects"><FontAwesomeIcon icon={faList} /> View Project Response</NavDropdown.Item>
                </NavDropdown>
                <NavDropdown title={<><FontAwesomeIcon icon={faUser} /> Profile</>} id="basic-nav-dropdown">
                  <NavDropdown.Item as={Link} to="/profile"><FontAwesomeIcon icon={faUser} /> View Profile</NavDropdown.Item>
                  <NavDropdown.Item as={Link} to="/addwallet"><FontAwesomeIcon icon={faWallet} /> Wallet Amount</NavDropdown.Item>
                </NavDropdown>
              </>
            )}
            {userRole === 'Freelancer' && (
              <>
                <NavDropdown title={<><FontAwesomeIcon icon={faTasks} /> My Service</>} id="basic-nav-dropdown">
                  <NavDropdown.Item as={Link} to="/addservice"><FontAwesomeIcon icon={faPlus} /> Add Service</NavDropdown.Item>
                  <NavDropdown.Item as={Link} to="/viewservice"><FontAwesomeIcon icon={faList} /> View Service</NavDropdown.Item>
                </NavDropdown>
                <NavDropdown title={<><FontAwesomeIcon icon={faTasks} /> Requests</>} id="basic-nav-dropdown">
                  <NavDropdown.Item as={Link} to="/servicerequest"><FontAwesomeIcon icon={faTasks} /> Service Request</NavDropdown.Item>
                  <NavDropdown.Item as={Link} to="/projectrequest"><FontAwesomeIcon icon={faTasks} /> Project request</NavDropdown.Item>
                </NavDropdown>
                <Nav.Link as={Link} to="/profile"><FontAwesomeIcon icon={faUser} /> Profile</Nav.Link>
              </>
            )}
            {!userRole && (
              <>
                <Nav.Link as={Link} to="/login"><FontAwesomeIcon icon={faSignInAlt} /> Login</Nav.Link>
                <Nav.Link as={Link} to="/register"><FontAwesomeIcon icon={faUserPlus} /> Register</Nav.Link>
              </>
            )}
          </Nav>
          {userRole && (
            <Button variant="outline-light" onClick={handleLogout}><FontAwesomeIcon icon={faSignOutAlt} /> Logout</Button>
          )}
        </Navbar.Collapse>
      </Container>
    </Navbar></>
  );
};

export default CustomNavbar;

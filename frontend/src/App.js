// src/App.js
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import React, { useState } from 'react';
import Home from './pages/Home';
import AboutUs from './pages/AboutUs';
import ContactUs from './pages/ContactUs';
import BaseNavbar from './component/common/BaseNavbar';
import Register from './component/common/Register';
import Login from './component/common/Login';
import AddCategory from './component/admin/AddCategory';
import ViewCategories from './component/admin/ViewCategories';
import RegisterAdmin from './component/admin/RegisterAdmin';
import AddService from './component/freelancer/AddService';
import ViewService from './component/freelancer/VIewService';
import ServiceRequest from './component/client/ServiceRequest';
import ViewServiceRequest from './component/client/ViewServiceRequest';
import ViewClientServiceRequest from './component/freelancer/ViewClientServiceRequest';
import ViewAllService from './component/admin/VIewAllService';
import ViewAllServiceRequest from './component/admin/ViewAllServiceRequest';
import AddProject from './component/client/AddProject';
import ViewProjectRequest from './component/client/ProjectRequest';
import ViewClientProject from './component/freelancer/ViewClientProject';
import Profile from './component/client/Profile';
import AddWallet from './component/client/AddWallet';
import SupportAdmin from './component/SupportAdmin';
import Feedback from './component/client/Feedback';
// import Projects from './pages/Projects';
// import Profile from './pages/Profile';


const App = () => {
  const [userRole, setUserRole] = useState('');

    // const handleLogout = () => {
    //     setUserType(null);
    //     // Additional logout logic if needed
    // };
    return (
        <Router> 
            {/* <BaseNavbar userType={userType} onLogout={handleLogout} /> */}
            <BaseNavbar userRole={userRole} setUserRole={setUserRole}  />
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/home" element={<Home userRole={userRole}/>} />
                <Route path="/about" element={<AboutUs />} />
                <Route path="/contact" element={<ContactUs />} />
                <Route path="/register" element={<Register />} />
                <Route path="/addcategory" element={<AddCategory />} />
                <Route path="/viewcategory" element={<ViewCategories />} />
                <Route path="/register-admin" element={<RegisterAdmin />} />
                <Route path="/addservice" element={<AddService />} />
                <Route path="/viewservice" element={<ViewService />} />
                <Route path="/allservice" element={<ViewAllService />} />
                <Route path="/allservicerequest" element={<ViewAllServiceRequest />} />
                <Route path="/servicerequest" element={<ViewClientServiceRequest />} />
                <Route path="/viewservice-request" element={<ViewServiceRequest />} />
                <Route path="/addproject" element={<AddProject />} />
                <Route path="/viewprojects" element={<ViewProjectRequest />} />
                <Route path="/projectrequest" element={<ViewClientProject />} />
                <Route path="/login" element={<Login setUserRole={setUserRole}/>} />
                <Route path="/profile" element={<Profile />} />
                <Route path="/addwallet" element={<AddWallet />} />
                <Route path="/support" element={<SupportAdmin />} />
                <Route path="/feedback/:serviceId" element={<Feedback />} />
                <Route path="/request-service/:categoryId" element={<ServiceRequest />} />

                {/* <Route path="/projects" element={<Projects />} />
                <Route path="/profile" element={<Profile />} /> */}
                {/* <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} /> */}
            </Routes>
        </Router>
    );
};

export default App;

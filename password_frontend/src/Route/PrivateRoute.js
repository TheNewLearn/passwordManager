import React from 'react';
import { Navigate } from "react-router-dom";

const isAuthenticated = () => {
    const token = sessionStorage.getItem('jwtToken');
    return !!token;
}

const PrivateRoute = ({children}) => {
    return isAuthenticated() ? children: <Navigate to="/loginForm"></Navigate>
}

export default PrivateRoute;
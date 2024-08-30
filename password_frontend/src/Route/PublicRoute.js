import React from 'react';
import { Navigate } from 'react-router-dom';

const PublicRoute = ({children}) => {
    const token = localStorage.getItem("jwtToken");
    if(token) return <Navigate to="/" replace />
    return children
}

export default PublicRoute;
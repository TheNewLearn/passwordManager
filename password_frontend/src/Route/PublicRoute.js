import React from 'react';
import { Navigate } from 'react-router-dom';

const PublicRoute = ({children}) => {
    const token = sessionStorage.getItem("jwtToken");
    if(token) return <Navigate to="/" replace />
    return children
}

export default PublicRoute;
import { Navigate } from 'react-router-dom';
import App from "../App";
import AuthForms from '../page/AuthForm';
import PrivateRoute from "./PrivateRoute";
import PublicRoute from "./PublicRoute";
const Routes = [
    {
        path:'/',
        element: <PrivateRoute><App /></PrivateRoute>
    },
    {
        path:"/loginForm",
        element: <PublicRoute><AuthForms mode='login'/></PublicRoute>
    },
    {
        path:"/registerForm",
        element: <PublicRoute><AuthForms mode='register'/></PublicRoute>
    },
    {
        path: '*',
        element: <Navigate to="/" replace />,
      }
]
export default Routes;
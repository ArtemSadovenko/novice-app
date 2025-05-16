import React from 'react';
import { Route, Link, useParams, RouteProps, useHistory } from 'react-router-dom';
import { useAuth } from '../context/AuthContext'; 
import { Login } from '@mui/icons-material';

interface ProtectedRouteProps extends RouteProps {
  redirectPath?: string;
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({
  redirectPath = "/login",
  ...routeProps
}) => {
  const { currentUser, loading } = useAuth();
  const history = useHistory()
  if (loading) {
    return <div>Loading...</div>;
  }
  
  if (!currentUser) {
    history.push(redirectPath)
  }
  if(!loading && currentUser){
  return <Route {...routeProps} />;
  }

  return <Route children={<Login/>} />;
  
};
export default ProtectedRoute;
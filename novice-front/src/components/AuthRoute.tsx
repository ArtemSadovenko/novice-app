import React from "react";
import { Route, Redirect, RouteProps } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

interface AuthRouteProps extends RouteProps {
  redirectPath?: string;
}

const AuthRoute: React.FC<AuthRouteProps> = ({
  redirectPath = "/dashboard",
  ...routeProps
}) => {
  const { currentUser, loading } = useAuth();
  
  if (loading) {
    // Show loading state while checking auth
    return <div>Loading...</div>;
  }
  
  if (currentUser) {
    return <Redirect to={redirectPath} />;
  }

  return <Route {...routeProps} />;
};

export default AuthRoute;
import "./App.css";
import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import ProtectedRoute from "./components/ProtectedRoute";
import Login from "./pages/auth/Login";
import Dashboard from "./pages/Dashboard";
import { CssBaseline, ThemeProvider } from "@mui/material";
import theme from "./theme";
import AuthRoute from "./components/AuthRoute";
import TopPage from "./pages/TopPage";
import Tournaments from "./pages/tournament/Tournaments";
import Register from "./pages/auth/Register";
import UserProfile from "./pages/profile/UserProfile";
import CreateTournament from "./pages/tournament/CreateTournament";
import TournamentPage from "./pages/tournament/TournamentPage";
import SelfProfile from "./pages/profile/SelfProfile";
import JudgeRoute from "./components/JudgeRoute";
import JudgeRooms from "./pages/room/JudgeRooms";
import ResponsibleJudgeRoute from "./components/ResponsibleJudjeRoute";
import JudgeRoom from "./pages/room/JudgeRoom";

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline>
        <AuthProvider>
          <Router>
            <Switch>
              <Route exact path="/">
                <Redirect to="/login" />
              </Route>

              <AuthRoute path="/login" component={Login} />
              <AuthRoute path="/register" component={Register} />
              <ProtectedRoute
                path="/dashboard"
                component={Dashboard}
              />
              <ProtectedRoute
                path="/toplist"
                component={TopPage}
              />
              <ProtectedRoute
                path="/tournament/create"
                component={CreateTournament}
              />
              <ProtectedRoute
                path="/tournament/:id"
                component={TournamentPage}
              />
              <ProtectedRoute
                path="/tournaments"
                component={Tournaments}
              />
              <ProtectedRoute
                path="/profile"
                component={SelfProfile}
              />
              <ProtectedRoute
                path="/user/:id"
                component={UserProfile}
              />

              <JudgeRoute
                path="/judge-rooms"
                component={JudgeRooms} />
              <Route
                path="/room/judge/:id"
                render={(props) => <ResponsibleJudgeRoute {...props} component={JudgeRoom} />}
              />
            </Switch>
          </Router>
        </AuthProvider>
      </CssBaseline>
    </ThemeProvider>
  );
}

export default App;

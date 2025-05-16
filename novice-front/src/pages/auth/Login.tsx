import { Box, Button, Grid, TextField, Typography } from "@mui/material";
import React, { useState } from "react";
import { useAuth } from "../../context/AuthContext";
import { Link, useHistory } from "react-router-dom";

function Login() {
  const auth = useAuth(); const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  const history = useHistory();
  const [input, setInput] = useState({
    email: "",
    password: ""
  })
  const [correctInput, setCorrectInput] = useState({
    email: true,
    password: true,
  })
  const handleLoginClick = () => {
    auth.login(input.email, input.password);
    history.push("/dashboard")
  };

  return (
    <Box
      sx={{
        height: "100vh",
        width: "100vw",
        bgcolor: 'background.default',
      }}
    >
      <Grid container>
        <Grid size={6} sx={{ height: "100vh" }}>
          <Box
            sx={{
              width: "100%",
              height: "100%",
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <Typography variant="h2">NOVICE</Typography>
          </Box>
        </Grid>
        <Grid
          size={6}
          sx={{
            height: "100vh",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <Box
            sx={{
              borderRadius: "20px",
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
              padding: "30px 20px 30px 20px",
              flexDirection: "column",
              bgcolor: 'background.paper',

            }}
          >

            {/* <TextField
                title="Email"
                placeholder="Email"
                onChange={(e) => setEmail(e.target.value)}
                sx={{
                    padding:"20px 0px 20px 0px"
                }}
              />
              <TextField
                title="Password"
                placeholder="Password"
                onChange={(e) => setPassword(e.target.value)}
                sx={{
                    padding:"0px 0px 20px 0px"
                }}
              /> */}

            <Typography sx={{ marginBottom: "20px" }}>Sign In</Typography>

            <TextField
              label="Email"

              placeholder="Email"
              error={!correctInput.email}
              onChange={(e) => {
                setInput(
                  prev => ({ ...prev, email: e.target.value.trim() })
                );
                setCorrectInput(
                  prev => ({ ...prev, email: emailRegex.test(e.target.value.trim()) })
                )
              }}
              sx={{
                padding: "0px 0px 20px 0px"
              }}

            />
            <TextField
              label="Password"
              title="Password"
              type="password"
              placeholder="Password"
              error={!correctInput.password}
              onChange={(e) => {
                setInput(
                  prev => ({ ...prev, password: e.target.value.trim() })
                );
                setCorrectInput(
                  prev => ({ ...prev, password: e.target.value.trim().length > 4 })
                )
              }}
              hidden
              sx={{
                padding: "0px 0px 20px 0px"
              }}
            />
            <Button variant="outlined" onClick={handleLoginClick}
              sx={{
                width: "100%",
                marginBottom:"20px"
              }}
            >Login</Button>

            <Link to="/register"> Sign Up</Link>
          </Box>
        </Grid>
      </Grid>
    </Box>
  );
}

export default Login;

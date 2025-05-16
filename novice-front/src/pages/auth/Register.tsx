
import { Box, Button, FormControl, Grid, InputLabel, MenuItem, Select, TextField, Typography } from "@mui/material";
import React, { useState } from "react";
import { useAuth } from "../../context/AuthContext";
import { Link, Redirect, useHistory } from "react-router-dom";
import { RegisterRequest, USER_EXPERIENCE, USER_ROLE } from "../../types/auth";
import { register } from "../../api/authapi";

function Register() {
    {
        const auth = useAuth();
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        const history = useHistory();
        const [input, setInput] = useState({
            email: "",
            password: "",
            repeatPassword: "",
            username: "",
            experience: USER_EXPERIENCE.NOVICE,
        })
        const [correctInput, setCorrectInput] = useState({
            email: true,
            password: true,
            repeatPassword: true,
            username: true,
            experience: true,
        })


        const validate = (): boolean => {

            return Object.values(correctInput).every(value => value === true);

        }

        const handleRegisterClick = () => {
            let res;
            if (validate()) {
                const data: RegisterRequest = {
                    email: input.email,
                    password: input.password,
                    experience: input.experience,
                    username: input.username,
                    roles: [USER_ROLE.USER],
                }
                res = auth.register(data)
            }
            if (res) {
                history.push("/login")
            }
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
                                padding: "40px 80px 40px 80px",
                                flexDirection: "column",
                                bgcolor: 'background.paper',

                            }}
                        >
                            <Typography sx={{ marginBottom: "20px" }}>Sign Up</Typography>

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
                                        prev => ({ ...prev, password: e.target.value.trim().length > 8 })
                                    )
                                }}
                                hidden
                                sx={{
                                    padding: "0px 0px 20px 0px"
                                }}
                            />

                            <TextField
                                label="Repeat Password"
                                title="Password"
                                type="password"
                                placeholder="Password"
                                error={!correctInput.repeatPassword}
                                onChange={(e) => {
                                    setInput(
                                        prev => ({ ...prev, repeatPassword: e.target.value.trim() })
                                    );
                                    setCorrectInput(
                                        prev => ({ ...prev, repeatPassword: e.target.value.trim() == input.password })
                                    )
                                }}
                                hidden
                                sx={{
                                    padding: "0px 0px 20px 0px"
                                }}
                            />

                            <TextField
                                label="Username"
                                title="Username"

                                placeholder="Username"
                                error={!correctInput.username}
                                onChange={(e) => {
                                    setInput(
                                        prev => ({ ...prev, username: e.target.value.trim() })
                                    );
                                    setCorrectInput(
                                        prev => ({ ...prev, username: e.target.value.trim().length > 3 })
                                    )
                                }}

                                sx={{
                                    padding: "0px 0px 20px 0px"
                                }}
                            />

                            <FormControl fullWidth>
                                <InputLabel id="demo-simple-select-label">Experience</InputLabel>
                                <Select
                                    labelId="demo-simple-select-label"
                                    id="demo-simple-select"
                                    value={input.experience}
                                    label="Experience"
                                    onChange={(e) => {
                                        setInput(
                                            prev => ({ ...prev, experience: e.target.value as USER_EXPERIENCE })
                                        )
                                    }}
                                >
                                    <MenuItem value={USER_EXPERIENCE.NOVICE}>NOVICE</MenuItem>
                                    <MenuItem value={USER_EXPERIENCE.EXPERIENCED}>EXPERIENCED</MenuItem>
                                    <MenuItem value={USER_EXPERIENCE.OLD}>OLD</MenuItem>

                                </Select>
                            </FormControl>

                            <Button variant="outlined" onClick={handleRegisterClick}
                                sx={{
                                    marginTop: "20px",
                                    marginBottom: "20px",
                                    width: "100%"
                                }}
                            >register</Button>

                            <Link to="/login"> Sign in</Link>
                        </Box>
                    </Grid>
                </Grid>
            </Box>
        );
    }
}

export default Register

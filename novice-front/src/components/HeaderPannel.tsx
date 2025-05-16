import { Box, Button, Typography } from '@mui/material'
import React from 'react'
import LogoutIcon from '@mui/icons-material/Logout';
import { useAuth } from '../context/AuthContext';
import ProfilrIcon from '@mui/icons-material/AccountCircle';
import { useHistory } from 'react-router-dom';

type HeaderPannelProps = {
    showProfile:boolean
}

function HeaderPannel(props: HeaderPannelProps) {
    const history = useHistory()
    const auth = useAuth()
    return (
        <Box sx={{
            padding: "3px 3px 0px 3px "
        }}>
            <Box
                sx={{
                    display: "flex",
                    justifyContent: "flex-end",
                    alignItems: "center",
                    bgcolor: "secondary.main",
                    borderRadius: "20px",
                    // border: "solid 1px",
                    borderColor: "black",
                    padding: "15px 20px 15px 20px"
                }}
            >
                <Button hidden={props.showProfile} endIcon={<ProfilrIcon />} sx={{
                    marginRight:"30px"
                }} onClick={() => history.push("/profile")}>Profile</Button>
                <Button endIcon={<LogoutIcon />} onClick={auth.logout}>Log out</Button>
            </Box>
        </Box>)
}

export default HeaderPannel

import React, { useEffect, useState } from 'react'
import { Box, Button, IconButton } from "@mui/material";
import DashboardIcon from '@mui/icons-material/Dashboard';
import CupIcon from '@mui/icons-material/EmojiEvents';
import TopIcon from '@mui/icons-material/WorkspacePremium';
import { useHistory } from 'react-router-dom';
import { getJudgeAllRoomDetails } from '../api/roomApi';
import { RoomDetails } from '../types/roomdetails';
import GavelIcon from '@mui/icons-material/Gavel';

function SidePannel() {
    const history = useHistory();
    const [isJudge, setIsJudje] = useState(false)
    const [rooms, setRooms] = useState<RoomDetails[]>()
    const [loading, setLoading] = useState(true)


    useEffect(() => {
        const fetchJudgeRooms = async () => {
            const data = await getJudgeAllRoomDetails()
            if (data) {
                setRooms(data)
                if (data.length != 0) {
                    setIsJudje(true)
                }
                setLoading(false)
            }
        }
        fetchJudgeRooms()
    }, [])

    return (
        <>
            <Box sx={{
                padding: "3px 3px 3px 3px ",
                height: "100vh",
                position: "fixed",
                width: "20%"

            }}>
                <Box sx={{
                    display: "flex",
                    height: "100%",
                    bgcolor: "secondary.main",
                    flexDirection: "column",
                    width: "100%",
                    borderRadius: "20px",
                    padding: "0px 10px 0px 10px "
                }}>
                    <Button
                        sx={{
                            marginTop: "60px",
                            padding: "20px 0px  20px 0px"
                        }}
                        onClick={() => {
                            history.push("/dashboard")
                        }}
                        startIcon={<DashboardIcon />}>Dashboard</Button>
                    <Button
                        onClick={() => {
                            history.push("/tournaments")
                        }}
                        sx={{
                            padding: "20px 0px  20px 0px"
                        }}
                        startIcon={<CupIcon />}>Tounaments</Button>

                    <Button
                        onClick={() => {
                            history.push("/toplist")
                        }}
                        sx={{
                            padding: "20px 0px  20px 0px"
                        }}
                        startIcon={<TopIcon />}>Top players</Button>

                    {!loading && isJudge ? (
                        <Button
                            onClick={() => {
                                history.push("/judge-rooms")
                            }}
                            sx={{
                                padding: "20px 0px  20px 0px"
                            }}
                            startIcon={<GavelIcon />}>My rooms</Button>

                    ) : null}
                </Box>
            </Box>
        </>
    )
}

export default SidePannel

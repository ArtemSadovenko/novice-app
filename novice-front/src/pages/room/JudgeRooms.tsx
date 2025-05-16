import React, { useEffect, useState } from 'react'
import withDashboardLayout from '../../components/withDashboardLayout'
import { RoomDetails, RoomPreview } from '../../types/roomdetails'
import { getJudgeAllRoomDetails, getJudgeAllRoomPreview } from '../../api/roomApi'
import { Box, Paper, Typography } from '@mui/material'
import { useHistory } from 'react-router-dom'

function JudgeRooms() {
    const history = useHistory()
    const [rooms, setRooms] = useState<RoomPreview[]>([])
    const [loading, setLoading] = useState(true)


    useEffect(() => {
        const fetchJudgeRooms = async () => {
            const data = await getJudgeAllRoomPreview()
            if (data) {
                setRooms(data)
                setLoading(false)
            }
        }
        fetchJudgeRooms()
    }, [])

    return (
        <Box>
            {loading && !rooms ? (<div>Loading...</div>) :
                (
                    <Box sx={{ display: 'flex', gap: 2, p: 2 }}>
                        {rooms.map(e =>
                            <Paper
                                key={e.id || e.roomName}
                                onClick={() => {
                                    history.push(`/room/judge/${e.id}`)
                                }}
                                elevation={3}
                                sx={{
                                    p: 3,
                                    mb: 2,
                                    borderRadius: '10px',
                                    transition: 'transform 0.2s ease-in-out',
                                    '&:hover': {
                                        transform: 'translateY(-5px)',
                                        boxShadow: '0 6px 20px rgba(0, 0, 0, 0.15)',
                                    }
                                }}
                            >
                                <Typography variant='h5'> {e.tournamentName}</Typography>
                                <Typography variant='h6'> Round: {e.roundNumber}</Typography>
                                <Typography variant='h6'> Room: {e.roomName}</Typography>
                            </Paper>)}
                    </Box>
                )
            }
        </Box>
    )
}

export default withDashboardLayout(JudgeRooms)

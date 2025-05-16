import React from 'react'
import { UserPreview } from '../types/auth'
import { Box, Grid, Typography } from '@mui/material'
import { useHistory } from 'react-router-dom'

interface ToplistUserPreviewProps {
  ordinal: number,
  user: UserPreview
}

function ToplistUserPreview(props: ToplistUserPreviewProps) {
  const history = useHistory()

  const handleClick = () => {
    history.push(`user/${props.user.id}`)
  } 
  
  return (
    <Box sx={{
      width: "100%",
      padding: "10px 10px 10px 10px",
      
      bgcolor: "background.paper",
      borderRadius: "20px",
      margin: "5px 0px 5px 0px ",
      cursor: "pointer"
    }}
    
    onClick={handleClick}
    >
      <Grid container   
         spacing={2}
        alignItems="center" justifyItems="center">
        <Grid size={1} justifyItems="center">
          <Typography>{props.ordinal}</Typography>
        </Grid>
        <Grid size={4} justifyItems="center">
          <Typography>{props.user.username}</Typography>
        </Grid>
        <Grid size={4} justifyItems="center">
          <Typography>{props.user.experience}</Typography>
        </Grid>
        <Grid size={2} justifyItems="center">
          <Typography>{props.user.averageScore}</Typography>
        </Grid>
      </Grid>
    </Box>
  )
}

export default ToplistUserPreview

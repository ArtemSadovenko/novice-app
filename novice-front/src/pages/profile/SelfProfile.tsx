import React from 'react'
import withDashboardLayout from '../../components/withDashboardLayout'
import { useAuth } from '../../context/AuthContext'
import { Box, Grid, Paper, Typography } from '@mui/material';

function SelfProfile() {
  const auth = useAuth();


  return (
    <>
    {
      auth.currentUser ? (
        <Box sx={{ p: 3 }}>
          <Paper elevation={3} sx={{ p: 4 }}>
            <Grid container spacing={3}>


              <Grid size={12} textAlign="center">
                <Typography variant="h4">{auth.currentUser.username}</Typography>
                <Typography variant="body1" color="textSecondary">{auth.currentUser.email}</Typography>
              </Grid>

              <Grid size={12} >
                <Typography variant="subtitle1">Experience</Typography>
                <Typography variant="h6">{auth.currentUser.experience || 'N/A'}</Typography>
              </Grid>

              <Grid size={12} >
                <Typography variant="subtitle1">Average Score</Typography>
                <Typography variant="h6">{auth.currentUser.averageScore || 'N/A'}</Typography>
              </Grid>

            </Grid>
          </Paper>
        </Box>
      ) : (<div>Loading</div>)
    }
    </>
  )
}

export default withDashboardLayout(SelfProfile, false)

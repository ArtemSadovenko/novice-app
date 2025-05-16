import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getUserById } from '../../api/userApi'; // Adjust the import path as needed
import { Box, Typography, CircularProgress, Avatar, Grid, Paper } from '@mui/material';
import { UserData } from '../../types/auth';
import withDashboardLayout from '../../components/withDashboardLayout';



function UserProfile() {
  const { id } = useParams<{ id: string }>();
  const [user, setUser] = useState<UserData | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchUserData = async () => {
      if (!id) return;
      
      try {
        setLoading(true);
        const userData = await getUserById(id);
        setUser(userData);
        setError(null);
      } catch (err) {
        setError('Failed to load user data');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchUserData();
  }, [id]);

  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100%' }}>
        <CircularProgress />
      </Box>
    );
  }

  if (error || !user) {
    return (
      <Box sx={{ textAlign: 'center', p: 3 }}>
        <Typography variant="h6" color="error">
          {error || 'User not found'}
        </Typography>
      </Box>
    );
  }

  return (
    <Box sx={{ p: 3 }}>
      <Paper elevation={3} sx={{ p: 4 }}>
        <Grid container spacing={3}>
          
          
          <Grid  size={12} textAlign="center">
            <Typography variant="h4">{user.username}</Typography>
            <Typography variant="body1" color="textSecondary">{user.email}</Typography>
          </Grid>

          <Grid  size={12} >
            <Typography variant="subtitle1">Experience</Typography>
            <Typography variant="h6">{user.experience || 'N/A'}</Typography>
          </Grid>

          <Grid  size={12} >
            <Typography variant="subtitle1">Average Score</Typography>
            <Typography variant="h6">{user.averageScore || 'N/A'}</Typography>
          </Grid>

        </Grid>
      </Paper>
    </Box>
  );
}

export default withDashboardLayout( UserProfile);
import React, { useState, useEffect } from 'react';
import withDashboardLayout from '../../components/withDashboardLayout';
import { Box, Typography, Button, Container, Grid, CircularProgress } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import { getAllTournament } from '../../api/tournamentApi';
import { Tournament } from '../../types/tournament';
import TournamentView from '../../components/TournamentView';
import { useHistory } from 'react-router-dom';

function Tournaments() {
  const [tournaments, setTournaments] = useState<Tournament[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const history = useHistory();

  useEffect(() => {
    const fetchTournaments = async () => {
      try {
        setLoading(true);
        const data = await getAllTournament();
        setTournaments(data);
        setError(null);
      } catch (err) {
        console.error('Failed to fetch tournaments:', err);
        setError('Failed to load tournaments. Please try again later.');
      } finally {
        setLoading(false);
      }
    };

    fetchTournaments();
  }, []);

  const handleCreateTournament = () => {
    history.push('/tournament/create');
  };

  return (
    <Container sx={{ padding:"20px 0px 20px 0px ", width: '100%' }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 4 }}>
        <Typography variant="h4" component="h1">
          Tournaments
        </Typography>
        <Button 
          variant="contained" 
          color="primary" 
          startIcon={<AddIcon />}
          onClick={handleCreateTournament}
        >
          Create Tournament
        </Button>
      </Box>

      {loading ? (
        <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
          <CircularProgress />
        </Box>
      ) : error ? (
        <Typography color="error" sx={{ mt: 2 }}>
          {error}
        </Typography>
      ) : tournaments.length === 0 ? (
        <Box sx={{ 
          mt: 4, 
          p: 4, 
          border: '1px dashed #ccc', 
          borderRadius: '8px', 
          textAlign: 'center' 
        }}>
          <Typography variant="h6" sx={{ mb: 2 }}>
            No tournaments found
          </Typography>
          <Typography variant="body1" color="text.secondary" sx={{ mb: 3 }}>
            Get started by creating your first tournament
          </Typography>
          <Button 
            variant="outlined" 
            color="primary"
            startIcon={<AddIcon />}
            onClick={handleCreateTournament}
          >
            Create Tournament
          </Button>
        </Box>
      ) : (
        <Grid container spacing={3}>
          {tournaments.map((tournament) => (
            <Grid size={4} key={tournament.id}>
              <TournamentView tournament={tournament} />
            </Grid>
            
          ))}
        </Grid>
      )}
    </Container>
  );
}

export default withDashboardLayout(Tournaments);
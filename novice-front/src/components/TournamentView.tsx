import React from 'react';
import { Paper, Typography, Chip, Box, Button } from '@mui/material';
import { Tournament, TOURNAMENT_STATUS } from '../types/tournament';
import { useHistory } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

interface TournamentViewProps {
  tournament: Tournament;
}

const TournamentView: React.FC<TournamentViewProps> = ({ tournament }) => {
  const history = useHistory();
  const auth = useAuth()

  const getStatusColor = (status: TOURNAMENT_STATUS) => {
    switch (status) {
      case TOURNAMENT_STATUS.NEW:
        return 'default';
      case TOURNAMENT_STATUS.OPEN:
        return 'primary';
      case TOURNAMENT_STATUS.IN_PROGRESS:
        return 'warning';
      case TOURNAMENT_STATUS.CLOSED:
        return 'success';
      default:
        return 'default';
    }
  };

  const getStatusLabel = (status: TOURNAMENT_STATUS) => {
    switch (status) {
      case TOURNAMENT_STATUS.NEW:
        return 'New';
      case TOURNAMENT_STATUS.OPEN:
        return 'Open for Registration';
      case TOURNAMENT_STATUS.IN_PROGRESS:
        return 'In Progress';
      case TOURNAMENT_STATUS.CLOSED:
        return 'Completed';
      default:
        return 'Unknown';
    }
  };

  const handleViewDetails = () => {
    history.push(`/tournament/${tournament.id}`);
  };

  return (
    <Paper
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
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', mb: 2 }}>
        <Typography variant="h5" component="h2" sx={{ fontWeight: 'bold' }}>
          {tournament.name}
        </Typography>
      <Box sx={{
        height:"30px",
        justifyContent:"flex-end"
      }}>
        {
          auth.currentUser ?
            tournament.judges.map(e => e.id).includes(auth.currentUser.id) ? <Chip sx={{ height:"100%"}} size="small" label="Judge" /> : null : null
        }
        <Chip
        sx={{height:"100%"}}
          label={getStatusLabel(tournament.tournamentStatus)}
          color={getStatusColor(tournament.tournamentStatus) as any}

        />
        </Box>
      </Box>

      <Box sx={{ mb: 2 }}>
        <Typography variant="body2" color="text.secondary" sx={{ mb: 1 }}>
          Topics: {Object.values(tournament.topics || {}).length}
        </Typography>
        <Typography variant="body2" color="text.secondary" sx={{ mb: 1 }}>
          Teams: {tournament.teams?.length || 0}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          Judges: {tournament.judges?.length || 0}
        </Typography>
      </Box>

      <Box sx={{ display: 'flex', justifyContent: 'flex-end' }}>
        <Button
          variant="contained"
          color="primary"
          size="small"
          onClick={handleViewDetails}
        >
          View Details
        </Button>
      </Box>
    </Paper>
  );
};

export default TournamentView;
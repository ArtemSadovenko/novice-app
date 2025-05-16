import React, { useEffect, useState } from 'react'
import withDashboardLayout from '../../components/withDashboardLayout'
import { useParams } from 'react-router-dom';
import { Tournament, TOURNAMENT_STATUS } from '../../types/tournament';
import { genarateRooms, getRoundTable, getTournamentById } from '../../api/tournamentApi';
import { Box, Button, CircularProgress, Grid, List, Paper, Typography } from '@mui/material';
import { UserData, UserPreview } from '../../types/auth';
import { getAllUsers, getAllUsersPreview } from '../../api/userApi';
import JoinTournamentDialog from '../../components/JoinTournamentDialog';
import { CreateTeamRequest, RegisterTeamRequest, Team } from '../../types/team';
import { useAuth } from '../../context/AuthContext';
import { registerTeam } from '../../api/teamApi';
import { RoundTable } from '../../types/room';
import RoundTableView from '../../components/Table';
import { tab } from '@testing-library/user-event/dist/tab';
import { ROUND_STATUS } from '../../types/roomdetails';

function TournamentPage() {
  const { id } = useParams<{ id: string }>();
  const [tournament, setTournament] = useState<Tournament | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [users, setUsers] = useState<UserData[]>([])
  const auth = useAuth()
  const [isDialogVisible, setIsDialogVisoble] = useState(false)
  const [isJudge, setIsJudje] = useState(false)
  const [table, setTable] = useState<RoundTable>()

  useEffect(() => {
    
    fetchtournament();
  }, [id]);

  useEffect(() => {
    const fetchTable = async () => {
      setLoading(true)
      if (tournament && tournament.roomDetails.length != 0 && !table) {
        const roundNumber = Math.max(...tournament.roomDetails.map(e => parseInt(e.roundNumber)))
        const table = await getRoundTable(tournament.id, roundNumber);
        setTable(table)
      }
      setLoading(false)
    }
    fetchTable()

  }, [tournament])

  useEffect(() => {
    if (auth.currentUser && tournament && tournament.judges) {
      setIsJudje(tournament.judges.map(e => e.id).includes(auth.currentUser.id));

    }
  }, [tournament])

  useEffect(() => {
    const fetchUsers = async () => {
      if (!tournament) return;

      const data: UserData[] = await getAllUsers();
      data.sort((a, b) => Number.parseFloat(b.averageScore) - Number.parseFloat(a.averageScore));

      let judgeIds = new Set(tournament.judges.map(j => j.id));
      if (auth.currentUser) {
        judgeIds.add(auth.currentUser.id)
      }
      const filtered = data.filter(user => !judgeIds.has(user.id));

      setUsers(filtered);
      setLoading(false);
    };

    fetchUsers();
  }, [tournament]);

  const fetchtournament = async () => {
    if (!id) return;

    try {
      setLoading(true);
      const data = await getTournamentById(id);
      setTournament(data);
      setError(null);
    } catch (err) {
      setError('Failed to load tournament data');
      console.error(err);
    }
  };

  const createTeam = async (team: CreateTeamRequest) => {
    const request: RegisterTeamRequest = {
      teamMembersIds: team.teamMembers.map(e => e.id),
      name: team.name,
      tournamentId: team.tournamentId
    }
    const newTeam: Team = await registerTeam(request)
    setLoading(true)
    setTournament(prev => {
      if (!prev) return prev;
      return {
        ...prev,
        teams: [...(prev.teams ?? []), newTeam],
      };
    });

    setLoading(false)
    setIsDialogVisoble(false)
  }

  const handleStartTournament = async () => {
    setLoading(true)
    if (tournament && tournament.id) {
      const rooms = await genarateRooms(tournament.id)
      setTable(rooms)
    }
    setLoading(false)
  }

  const handleStartNextRoundClicked = async () => {
    if (tournament?.id) {
      setLoading(true);
      const rooms = await genarateRooms(tournament.id);
      setTable(rooms);
      fetchtournament()
      setLoading(false);
    }
  };


  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100%' }}>
        <CircularProgress />
      </Box>
    );
  }


  if (error || !tournament) {
    return (
      <Box sx={{ textAlign: 'center', p: 3 }}>
        <Typography variant="h6" color="error">
          {error || 'User not found'}
        </Typography>
      </Box>
    );
  }

  return (
    <Box>
      <Box sx={{ padding: "20px 10px 20px 10px " }}>
        <Paper elevation={3} sx={{ p: 4 }}>
          <Grid container spacing={3}>
            {loading ? <div>Loading...</div> : (
              <JoinTournamentDialog setIsVisible={setIsDialogVisoble} createTeam={createTeam} users={users} isVisible={isDialogVisible} tournamentId={tournament.id}></JoinTournamentDialog>
            )}
            <Grid size={12} textAlign="center">
              <Typography variant="h4">{tournament.name}</Typography>

            </Grid>

            <Grid size={12} >
              <List>
                {Object.entries(tournament.topics).map(([key, value]) => (
                  <Paper sx={{ bgcolor: "secondary.main", margin: "10px 0px 10px 0px " }} key={key}>
                    Round {key}: {value}
                  </Paper>
                ))}
              </List>
            </Grid>
            <Grid size={12} >
              <Typography>Judjes:</Typography>
              <List>
                {tournament.judges.map(judje => (
                  <Paper sx={{ bgcolor: "secondary.main", margin: "10px 0px 10px 0px " }} key={judje.id}>
                    {judje.username}
                  </Paper>
                ))}
              </List>
            </Grid>
            <Grid size={12} >
              <Typography>Teams:</Typography>
              <List>
                {tournament.teams.map((team, i) => (
                  <Grid container key={i} spacing={1}>
                    <Grid size={4}>
                      {team.name}
                    </Grid>
                    {team.teamMembers.map(e => (
                      <Grid size={4} key={e.id}>
                        {e.username}
                      </Grid>
                    ))}
                  </Grid>
                ))}

              </List>

            </Grid>
            {(tournament.tournamentStatus === TOURNAMENT_STATUS.NEW || tournament.tournamentStatus === TOURNAMENT_STATUS.OPEN) ? (
              isJudge ? (
                <Grid size={12}>
                  <Button sx={{ width: "100%" }} onClick={handleStartTournament} variant='contained'>Start Tournament</Button>
                </Grid>
              ) : (
                <Grid size={12}>
                  <Button sx={{ width: "100%" }} variant='contained' onClick={() => setIsDialogVisoble(true)}>Join</Button>
                </Grid>
              )
            ) : (
              <Grid size={12}>
                {table ? (
                  <RoundTableView tableData={table} />
                ) : null}
              </Grid>
            )}

            {tournament.tournamentStatus == TOURNAMENT_STATUS.IN_PROGRESS
              && tournament.roomDetails
                .filter(e => e.roundStatus == ROUND_STATUS.COMPLETED)
                .length == 0 ?
              (
                <Grid size={12}>
                  <Button sx={{ width: "100%" }} variant='contained' onClick={handleStartNextRoundClicked}>Start Next Round</Button>
                </Grid>
              )
              : null}

          </Grid>
        </Paper>
      </Box>
    </Box >
  )
}

export default withDashboardLayout(TournamentPage)

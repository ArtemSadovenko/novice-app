import React, { useEffect, useState } from 'react'
import withDashboardLayout from '../../components/withDashboardLayout'
import { Box, Button, IconButton, List, ListItem, TextField } from '@mui/material'
import { CreateTournamentRequest } from '../../types/tournament'
import DeleteIcon from '@mui/icons-material/Delete';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import { UserData } from '../../types/auth';
import { getAllUsers } from '../../api/userApi';
import OutlinedInput from '@mui/material/OutlinedInput';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import Chip from '@mui/material/Chip';
import { createTournament } from '../../api/tournamentApi';
import { useHistory } from 'react-router-dom';




const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 250,
    },
  },
};

function CreateTournament() {
  const [tournament, setTournament] = useState<CreateTournamentRequest>({
    name: '',
    topics: {},
    judges: [],
  })
  const [correctInput, setCorrectInput] = useState({
    name: true,
  })
  const [newTopic, setNewTopic] = useState('');
  const history = useHistory()

  const [users, setUsers] = useState<UserData[]>([]);

  const [buttonLoading, setButtonLoading] = useState(false)

  const handleChange = (event: SelectChangeEvent<UserData[]>) => {
    const {
      target: { value },
    } = event;

    const selectedUsers = value as unknown as UserData[];

    setTournament(prev => ({
      ...prev,
      judges: selectedUsers,
    }));
  };





  useEffect(() => {
    const fetchUsers = async () => {
      const data: UserData[] = await getAllUsers();
      data.sort((a, b) => Number.parseFloat(b.averageScore) - Number.parseFloat(a.averageScore))
      setUsers(data);
    };

    fetchUsers();
  }, []);

  const handleAddTopic = () => {
    if (newTopic.trim().length === 0) return; // don't add empty topic

    setTournament(prev => {
      const newTopics = { ...prev.topics };
      const nextIndex = Object.keys(newTopics).length + 1;
      newTopics[nextIndex.toString()] = newTopic.trim();
      return { ...prev, topics: newTopics };
    });

    setNewTopic(''); // clear input after adding
  };

  const handleDeleteTopic = (keyToDelete: string) => {
    setTournament(prev => {
      const topicsArray = Object.entries(prev.topics)
        .filter(([key]) => key !== keyToDelete) // remove the clicked one
        .map(([, topic]) => topic); // just keep topic values

      const newTopics: Record<string, string> = {};
      topicsArray.forEach((topic, index) => {
        newTopics[(index + 1).toString()] = topic;
      });

      return { ...prev, topics: newTopics };
    });
  };


  return (
    <>
      <Box sx={{
        width: "100%",
        padding: "20px 10px 20px 10px"
      }}>
        <Box sx={{
          bgcolor: "background.paper",
          width: "100%",
          padding: "10px 20px 10px 20px",
          borderRadius: "20px",
          display: "flex",
          flexDirection: "column"
        }}>

          <TextField
            label="Lable"

            placeholder="New Cup"
            error={!correctInput.name}
            onChange={(e) => {
              const name = e.target.value.trim()
              setTournament(
                prev => ({ ...prev, name: name })
              );
              setCorrectInput(
                prev => ({ ...prev, name: name.length > 3 })
              )
            }}
            sx={{
              padding: "0px 0px 20px 0px"
            }}

          />
          <Box sx={{ display: 'flex', gap: 2, marginBottom: 2 }}>
            <TextField
              label="New Topic"
              value={newTopic}
              onChange={(e) => setNewTopic(e.target.value)}
              fullWidth
            />

            <IconButton onClick={handleAddTopic}><AddCircleOutlineIcon /></IconButton>
          </Box>

          {/* List of topics */}
          <List>
            {Object.entries(tournament.topics).map(([key, topic]) => (
              <ListItem key={key}>
                round {key}: {topic}
                <IconButton onClick={() => handleDeleteTopic(key)}>
                  <DeleteIcon />
                </IconButton>
              </ListItem>
            ))}
          </List>



          <FormControl sx={{ m: 1 }}>
            <InputLabel id="judge-select-label">Judges</InputLabel>
            <Select
              labelId="judge-select-label"
              id="judge-select"
              multiple
              value={tournament.judges.map(judge => judge.id)}
              onChange={(e) => {
                const selectedIds = e.target.value as string[];
                const selectedUsers = selectedIds.map(id =>
                  users.find(user => user.id === id)
                ).filter((user): user is UserData => user !== undefined);

                setTournament(prev => ({
                  ...prev,
                  judges: selectedUsers,
                }));
              }}
              input={<OutlinedInput id="select-multiple-chip" label="Judges" />}
              renderValue={(selected) => (
                <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
                  {tournament.judges.map((judge) => (
                    <Chip
                      key={judge.id}
                      label={judge.username}

                    />
                  ))}
                </Box>
              )}
              MenuProps={MenuProps}
            >
              {users.map((user) => (
                <MenuItem key={user.id} value={user.id}>
                  {user.username}
                </MenuItem>
              ))}
            </Select>
          </FormControl>


          <Button
            variant="contained"
            color="primary"
            disabled={tournament.name.length < 3 || Object.keys(tournament.topics).length === 0 || tournament.judges.length === 0}
            onClick={() => {
              setButtonLoading(true)
              createTournament(tournament)
              history.push("/tournaments")
            }}
            loading={buttonLoading}
          >
            Create Tournament
          </Button>

        </Box>
      </Box>
    </>
  )
}

export default withDashboardLayout(CreateTournament)

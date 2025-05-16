import React, { useEffect, useState } from 'react'
import { useHistory, useParams } from 'react-router-dom'
import { CreateRoundResultsRequest, PLACE, POSITION, RoomDetails, RoomPosition, RoundResults } from '../../types/roomdetails'
import { getRoomDetailsById, setRoomResults } from '../../api/roomApi'
import { DatasetLinked } from '@mui/icons-material'
import withDashboardLayout from '../../components/withDashboardLayout'
import { Box, Button, Grid, IconButton, List, ListItem, ListItemText, Typography } from '@mui/material'
import { Team } from '../../types/team'
import UpIcon from '@mui/icons-material/ArrowDropUp';
import DownIcon from '@mui/icons-material/ArrowDropDown';


const PLACE_REVERSE: Record<string, PLACE> = {
  "1": PLACE.FIRST,
  "2": PLACE.SECOND,
  "3": PLACE.THIRD,
  "4": PLACE.FOURTH
}


function JudgeRoom() {
  const { id } = useParams<{ id: string }>()
  const [loading, setLoading] = useState(true)
  const [room, setRoom] = useState<RoomDetails>()
  const [orderedTeams, setOrderedTeams] = useState<RoomPosition[]>([])
  const history = useHistory()

  useEffect(() => {
    const fetchRoom = async () => {
      let data = null
      if (id) {
        data = await getRoomDetailsById(id)
      }
      if (data) {
        setRoom(data)
        setOrderedTeams(data.positions)
        setLoading(false)
      }
    }
    fetchRoom()
  }, [])

  const getPositionText = (position: POSITION) => {
    switch (position) {
      case (POSITION.OPENING_PROPOSITION):
        return "OG"

      case (POSITION.OPENING_OPPOSITION):
        return "OO"

      case (POSITION.CLOSING_PROPOSITION):
        return "CG"

      case (POSITION.CLOSING_OPPOSITION):
        return "CO"

    }
  }

  const handleUpClick = (index: number) => {
    if (index === 0) return
    const newOrder = [...orderedTeams]
    const temp = newOrder[index - 1]
    newOrder[index - 1] = newOrder[index]
    newOrder[index] = temp
    setOrderedTeams(newOrder)
  }


  const handleDownClick = (index: number) => {
    if (index === orderedTeams.length - 1) return
    const newOrder = [...orderedTeams]
    const temp = newOrder[index + 1]
    newOrder[index + 1] = newOrder[index]
    newOrder[index] = temp
    setOrderedTeams(newOrder)
  }

  const handleConfirmClicked = () => {
    let results: CreateRoundResultsRequest[] = []
    orderedTeams.forEach((pos, i) => {
      const result: CreateRoundResultsRequest = {
        team: pos.team,
        roomDetailsId: pos.roomDetailsId,
        speakerPoints: {
          [pos.team.teamMembers[0].id]: "0",
          [pos.team.teamMembers[1].id]: "0",
        },
        place: PLACE_REVERSE[`${i + 1}`]
      }
      results.push(result)
    })
    if (room) {
      setRoomResults(room.id, results)
      history.push("/dashboard")
    }
  }

  return (
    <>
      {room && !loading ? (
        <Box sx={{
          width: "100%",
          display: "flex",
          justifyContent: "center"
        }}>
          <Box sx={{
            // bgcolor:"secondary.main",
            padding: "20px 10px 20px 10px ",
            margin: "20px 10px 20px 10px",
            borderRadius: "20px",
            width: "50%"

          }}>
            <Grid container spacing={2}>
              <Grid size={4} justifyContent='center'>
                <List sx={{ width: "auto" }}>
                  {orderedTeams.map((e, i) => (
                    <ListItem sx={{
                      height: "6rem",
                      margin: "10px 0px 10px 0px ",
                      bgcolor: "secondary.main",
                      borderRadius: "10px",
                      display: "flex",
                      alignContent: "center",
                      justifyContent: "center"
                    }} key={i}>
                      <Typography variant='h4'>{i + 1} Place:</Typography>
                    </ListItem>
                  ))}
                </List>
              </Grid>
              <Grid size={6}>
                <List>
                  {orderedTeams.map((position, i) => (

                    <ListItem sx={{
                      height: "6rem",
                      margin: "10px 0px 10px 0px ",
                      bgcolor: "secondary.main",
                      borderRadius: "10px",
                      display: "flex",
                      justifyContent: "space-between"
                    }} key={i}>
                      <Box>
                        <Typography variant='h4'>{getPositionText(position.position)}: {position.team.name}</Typography>
                      </Box>
                      <Box sx={{
                        display: 'flex',
                        flexDirection: "column"
                      }}>
                        <IconButton onClick={() => handleUpClick(i)}>
                          <UpIcon />
                        </IconButton>
                        <IconButton onClick={() => handleDownClick(i)}>
                          <DownIcon />
                        </IconButton>
                      </Box>
                    </ListItem>
                  ))}
                </List>
              </Grid>

            </Grid>
            <Grid container >
              <Grid size={10}>
                <Button sx={{ width: "100%" }} variant='contained' onClick={handleConfirmClicked}>Confirm</Button>
              </Grid>
            </Grid>
          </Box>
        </Box>
      ) : (
        <div>Loading...</div>)}
    </>
  )
}

export default withDashboardLayout(JudgeRoom)

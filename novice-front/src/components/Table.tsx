import React from 'react'
import { RoundTable } from '../types/room'
import { Box, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography } from '@mui/material'
import { POSITION } from '../types/roomdetails'

type TableProps = {
    tableData: RoundTable
}

function RoundTableView(props: TableProps) {
    return (
        <>
        <Typography variant='h4'>Round {props.tableData.roundNumber}</Typography>
        <TableContainer sx={{bgcolor:"secondary.main"}} component={Paper}>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>OG</TableCell>
                        <TableCell>OO</TableCell>
                        <TableCell>CG</TableCell>
                        <TableCell>CO</TableCell>
                        <TableCell>Judge</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {props.tableData.rooms.map(room => (
                        <TableRow key={room.roomDetailsId}>
                            <TableCell>{room.teams[POSITION.OPENING_PROPOSITION].name}</TableCell>
                            <TableCell>{room.teams[POSITION.OPENING_OPPOSITION] ? room.teams[POSITION.OPENING_OPPOSITION].name : ""}</TableCell>
                            <TableCell>{room.teams[POSITION.CLOSING_PROPOSITION] ? room.teams[POSITION.CLOSING_PROPOSITION].name : ""}</TableCell>
                            <TableCell>{room.teams[POSITION.CLOSING_OPPOSITION] ? room.teams[POSITION.CLOSING_OPPOSITION].name : ""}</TableCell>
                            <TableCell>{room.judges.map(e => e.username + " ")}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
        </>
    )
}

export default RoundTableView

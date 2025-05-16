package com.novice.debatenovice.controller;


import com.novice.debatenovice.dto.TournamentDTO;
import com.novice.debatenovice.dto.table.RoundTableDTO;
import com.novice.debatenovice.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tournament")
public class TournamentController {
    @Autowired
    private TournamentService tournamentService;

    @PostMapping
            (consumes = {MediaType.APPLICATION_JSON_VALUE},
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TournamentDTO> createTournament(@RequestBody TournamentDTO tournament) {
        TournamentDTO tournamentResult = tournamentService.createTournament(tournament);
        return new ResponseEntity<>(tournamentResult, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TournamentDTO>> getAllTournaments() {
        List<TournamentDTO> tournamentResult = tournamentService.getAllTournament();
        return new ResponseEntity<>(tournamentResult, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentDTO> getTournament(@PathVariable Long id) {
        TournamentDTO tournamentResult = tournamentService.getTournament(id);
        return new ResponseEntity<>(tournamentResult, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id) {
        tournamentService.deleteTournament(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<TournamentDTO> updateTournament(@RequestBody TournamentDTO tournament) {
        return new ResponseEntity<>(tournamentService.updateTournament(tournament), HttpStatus.OK);
    }

    @PostMapping("/{tournamentId}/generate-rooms")
    public ResponseEntity<RoundTableDTO> generateRooms(@PathVariable Long tournamentId) {
        return new ResponseEntity<>(tournamentService.generateRoundTable(tournamentId), HttpStatus.OK);
    }

    @GetMapping("/{tournamentId}/{roundNumber}")
    public ResponseEntity<RoundTableDTO> getRoundTable(@PathVariable Long tournamentId, @PathVariable Integer roundNumber) {
        return new ResponseEntity<>(tournamentService.getRoundTable(tournamentId, roundNumber), HttpStatus.OK);
    }
}




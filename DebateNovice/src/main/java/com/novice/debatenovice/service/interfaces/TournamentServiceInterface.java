package com.novice.debatenovice.service.interfaces;


import com.novice.debatenovice.dto.TournamentDTO;

import java.util.List;

public interface TournamentServiceInterface {

    TournamentDTO createTournament(TournamentDTO tournament);

    TournamentDTO updateTournament(TournamentDTO tournament);

    void deleteTournament(Long id);

    List<TournamentDTO> getAllTournament();

    TournamentDTO getTournament(Long id);
}

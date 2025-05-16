package com.novice.debatenovice.controller;

import com.novice.debatenovice.dto.team.SimpleRegisterTeamDTO;
import com.novice.debatenovice.dto.team.TeamDTO;
import com.novice.debatenovice.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping
    public ResponseEntity<List<TeamDTO>> findAllTeams() {
        return new ResponseEntity<>(teamService.findAllTeams(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@RequestBody TeamDTO teamDTO) {
        return new ResponseEntity<>(teamService.createTeam(teamDTO), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<TeamDTO> updateTeam(@RequestBody TeamDTO teamDTO) {
        return new ResponseEntity<>(teamService.updateTeam(teamDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> findTeamById(@PathVariable Long id) {
        return new ResponseEntity<>(teamService.findTeamById(id), HttpStatus.OK);
    }

    @PostMapping("/regiter")
    public ResponseEntity<TeamDTO> regiterTeam(@RequestBody SimpleRegisterTeamDTO teamDTO) {
        return new ResponseEntity<>(teamService.createTeam(teamDTO), HttpStatus.OK);
    }

    @PostMapping("/all")
    public ResponseEntity<List<TeamDTO>> createAll(@RequestBody List<TeamDTO> teamDTOs) {
        return new ResponseEntity<>(teamService.createAll(teamDTOs), HttpStatus.CREATED);
    }
}

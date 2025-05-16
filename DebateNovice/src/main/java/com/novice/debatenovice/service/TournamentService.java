package com.novice.debatenovice.service;

import com.novice.debatenovice.converter.RoomDetailsConverter;
import com.novice.debatenovice.converter.TeamConverter;
import com.novice.debatenovice.converter.TournamentConverter;
import com.novice.debatenovice.converter.UserConverter;
import com.novice.debatenovice.dto.RoundResultsDTO;
import com.novice.debatenovice.dto.TournamentDTO;
import com.novice.debatenovice.dto.UserDTO;
import com.novice.debatenovice.dto.room.RoomDTO;
import com.novice.debatenovice.dto.room.RoomDetailsDTO;
import com.novice.debatenovice.dto.room.RoomPositionDTO;
import com.novice.debatenovice.dto.table.RoundTableDTO;
import com.novice.debatenovice.dto.team.TeamDTO;
import com.novice.debatenovice.entity.RoomDetails;
import com.novice.debatenovice.entity.Tournament;
import com.novice.debatenovice.enums.POSITION;
import com.novice.debatenovice.enums.ROUND_STATUS;
import com.novice.debatenovice.enums.TOURNAMENT_STATUS;
import com.novice.debatenovice.exeptions.CustomException;
import com.novice.debatenovice.repository.TournamentRepository;
import com.novice.debatenovice.service.interfaces.TournamentServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TournamentService implements TournamentServiceInterface {
    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private TournamentConverter tournamentConverter;
    @Autowired
    private TeamConverter teamConverter;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomDetailsConverter roomDetailsConverter;
    @Autowired
    private TeamService teamService;


    @Override
    public TournamentDTO createTournament(TournamentDTO tournament) {
        tournament.setTournamentStatus(TOURNAMENT_STATUS.NEW);
        return tournamentConverter.toDTO(tournamentRepository
                .save(tournamentConverter.toEntity(tournament)));
    }

    @Override
    public TournamentDTO updateTournament(TournamentDTO tournament) {
        return tournamentConverter.toDTO(tournamentRepository
                .save(tournamentConverter.toEntity(tournament)));
    }

    @Override
    public void deleteTournament(Long id) {
        tournamentRepository.deleteById(id);
    }

    @Override
    public List<TournamentDTO> getAllTournament() {
        return tournamentRepository.findAll().stream()
                .map(tournamentConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public TournamentDTO getTournament(Long id) {
        return tournamentConverter.toDTO(tournamentRepository
                .findById(id)
                .orElseThrow(() -> new CustomException("Tournament not found")));
    }

//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Builder
//    private class Pack {
//        private TeamDTO OP;
//        private TeamDTO OO;
//        private TeamDTO CP;
//        private TeamDTO CO;
//
//    }
//
//    public List<Pack> breakByPacks(List<TeamDTO> teams, Integer amount) {
//        List<Pack> packs = new ArrayList<>(amount);
//        for (int i = 0; i < amount; i++) {
//
//        }
//
//    }

//    private List<RoomDTO> randomlyGenerate(List<TeamDTO> teams, List<UserDTO> judges) {
//        Integer roomAmount = teams.size() % 4 ==0 ?teams.size()/4 : teams.size()/4 +1;
//        List<RoomDTO> rooms;// = new ArrayList<>(roomAmount);
//        List<TeamDTO> unFullRoom ;
//        Collections.shuffle(teams);
//        Collections.shuffle(judges);
//
//
//        if (judges.size() < roomAmount) {
//            throw new CustomException("Now enough judges for rooms. Judge amount:" + judges.size() + "Room amount:" + roomAmount);
//        }
//
//        Integer judgeIndex = 0;
//
//
//
//    }

//    private List<RoomDTO> randomlyGenerate(List<TeamDTO> teams, List<UserDTO> judges) {
//        List<RoomDTO> rooms = new ArrayList<>();
//        Collections.shuffle(teams);
//        Collections.shuffle(judges);
//
//        Integer roomAmount = teams.size() % 4 ==0 ?teams.size()/4 : teams.size()/4 +1;
//
//        if (judges.size() < roomAmount) {
//            throw new CustomException("Now enough judges for rooms. Judge amount:" + judges.size() + "Room amount:" + roomAmount);
//        }
//
//        Integer judgeIndex = 0;
//        for (int i = 0; i < roomAmount; i+=4) {
//            rooms.add(new RoomDTO(
//                    Map.of(
//
//                            Position.OPENING_PROPOSITION, teams.get(0+i),
//                            Position.OPENING_OPPOSITION, teams.get(1+i),
//                            Position.CLOSING_PROPOSITION, teams.get(2+i),
//                            Position.CLOSING_OPPOSITION, teams.get(3+i)
//                    ),
//                    List.of(judges.get(judgeIndex)) //TODO fix multiple judges apply
//
//            ));
//            judgeIndex++;
//        }
//
//
//    }


    private List<RoomDTO> randomlyGenerate(List<TeamDTO> teamsOrigin, List<UserDTO> judgesOrigin) {
        List<TeamDTO> teams = new ArrayList<>(teamsOrigin);
        List<UserDTO> judges = new ArrayList<>(judgesOrigin);

        List<RoomDTO> rooms = new ArrayList<>();
        Collections.shuffle(teams);
        Collections.shuffle(judges);

        Integer roomAmount = teams.size() % 4 == 0 ? teams.size() / 4 : teams.size() / 4 + 1;

        if (judges.size() < roomAmount) {
            throw new CustomException("Now enough judges for rooms. Judge amount:" + judges.size() + "Room amount:" + roomAmount);
        }

        Integer judgeOrdinal = 0;
        for (int i = 0; i < roomAmount; i++) {
            rooms.add(new RoomDTO());
            int k = Math.min(i * 4 + 4, teams.size());
            Map<POSITION, TeamDTO> map = new HashMap<>();
            int enumOrdinal = 1;
            for (int j = i * 4; j < k; j++) {
                map.put(POSITION.fromInt(enumOrdinal), teams.get(j));
                enumOrdinal++;
            }
            rooms.get(i).setJudges(List.of(judges.get(judgeOrdinal)));
            judgeOrdinal++;
            rooms.get(i).setTeams(map);
        }

        return rooms;
    }


    private List<RoomDTO> generateByScore(List<TeamDTO> teamsOrigin, List<UserDTO> judges) {
        List<TeamDTO> teams = new ArrayList<>(teamsOrigin);
        List<RoomDTO> rooms = new ArrayList<>();
        teams.sort(Comparator.comparing(TeamDTO::getTeamPoints).reversed());
        Collections.shuffle(judges);

        Integer roomAmount = teams.size() % 4 == 0 ? teams.size() / 4 : teams.size() / 4 + 1;

        if (judges.size() < roomAmount) {
            throw new CustomException("Now enough judges for rooms. Judge amount:" + judges.size() + "Room amount:" + roomAmount);
        }

        Integer judgeOrdinal = 0;
        for (int i = 0; i < roomAmount; i++) {
            rooms.add(new RoomDTO());
            int k = Math.min(i * 4 + 4, teams.size() - 1);
            Map<POSITION, TeamDTO> map = new HashMap<>();
            int enumOrdinal = 1;
            for (int j = i * 4; j < k - i * 4; j++) {
                map.put(POSITION.fromInt(enumOrdinal), teams.get(j));
                enumOrdinal++;
            }
            rooms.get(i).setJudges(List.of(judges.get(judgeOrdinal)));
            judgeOrdinal++;
            rooms.get(i).setTeams(map);
        }

        return rooms;
    }
//
//    public RoundTableDTO generateRoundTable(Long tournamentId) {
//        RoomDetailsDTO roomDetailsDTO = new RoomDetailsDTO();
//        List<RoomDTO> rooms;
//        Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow(() -> new CustomException("Tournament not found"));
//        Integer roundNumber = tournament.getRoomDetails() == null ? 1 : tournament.getRoomDetails().size() + 1;
//        List<TeamDTO> teams = tournament.getTeams().stream().map(teamConverter::toTeamDTO).toList();
//        if (roundNumber == 1) {
//            rooms = randomlyGenerate(teams, tournament.getJudges().stream().map(userConverter::toDTO).toList());
//        }
//        else {
//            rooms = generateByScore(teams, tournament.getJudges().stream().map(userConverter::toDTO).toList());
//        }
//
//        roomDetailsConverter
//
//
//        roomDetailsDTO = roomService.saveRoomDetails(roomDetailsDTO);
//
//        return RoundTableDTO.builder()
//                .tournamentId(tournamentId)
//                .roundNumber(roundNumber)
//                .rooms(rooms)
//                .build();
//    }

//    private List<List<TeamDTO>> brakeToTeamsOf4(List<TeamDTO> teams) {
//        List<List<TeamDTO>> result = new ArrayList<>();
//        Integer maxIndex = teams.size() - 1;
//        Integer startIndex = 0;
//        Integer endIndex = startIndex + 4 > maxIndex ? maxIndex : startIndex + 4;
//        while (startIndex <= maxIndex) {
//            result.add(teams.subList(startIndex, endIndex));
//            startIndex = startIndex + 4;
//            endIndex = endIndex + 4 > maxIndex ? maxIndex : endIndex + 4;
//        }
//        return result;
//    }

    private List<List<TeamDTO>> brakeToTeamsOf4(List<TeamDTO> teams) {
        List<List<TeamDTO>> result = new ArrayList<>();
        int size = teams.size();
        for (int startIndex = 0; startIndex < size; startIndex += 4) {
            int endIndex = Math.min(startIndex + 4, size);
            result.add(teams.subList(startIndex, endIndex));
        }
        return result;
    }


    private List<RoomDetailsDTO> randomlyGenerateRooms(List<RoomDetailsDTO> rooms, List<UserDTO> judgesOrigin, List<TeamDTO> teamsOrigin) {
        List<RoomDetailsDTO> roomDetails = new ArrayList<>(rooms);
        List<UserDTO> judges = new ArrayList<>(judgesOrigin);
        List<TeamDTO> teams = new ArrayList<>(teamsOrigin);
        Collections.shuffle(judges);
        Collections.shuffle(teams);
        List<List<TeamDTO>> teamBraked = brakeToTeamsOf4(teams);

        for (int i = 0; i < roomDetails.size(); i++) {
            List<RoomPositionDTO> positions = new ArrayList<>();
            for (int j = 0; j < teamBraked.get(i).size(); j++) {
                positions.add(RoomPositionDTO.builder()
                        .roomDetailsId(roomDetails.get(i).getId())
                        .position(POSITION.fromInt(j + 1))
                        .team(teamBraked.get(i).get(j))
                        .build());
            }
            roomDetails.get(i).setJudges(List.of(judges.get(i)));
            roomDetails.get(i).setPositions(positions);
            roomDetails.get(i).setRoomName(String.valueOf(i + 1));
        }

        return roomDetails;
    }

    private List<TeamDTO> sortTeamsByRating(List<RoundResultsDTO> results) {
        Map<Long, Double> map = new HashMap<>();
        results.forEach(result -> {
            if (map.containsKey(result.getTeam().getId())) {
                map.put(result.getTeam().getId(), map.get(result.getTeam().getId()) + result.getPlace().getPoints());
            } else {
                map.put(result.getTeam().getId(), 0.0);
            }
        });
        Set<Map.Entry<Long, Double>> sortedEntries = map.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        List<TeamDTO> teams = new ArrayList<>(sortedEntries.size());
        for (Map.Entry<Long, Double> entry : sortedEntries) {
            teams.add(teamService.findTeamById(entry.getKey()));
        }
        return teams;
    }

    private List<RoomDetailsDTO> generateRoomsByScore(List<RoomDetailsDTO> history, List<RoomDetailsDTO> rooms, List<UserDTO> judgesOrigin, List<TeamDTO> teamsOrigin) {
        List<RoomDetailsDTO> roomDetails = new ArrayList<>(rooms);
        List<UserDTO> judges = new ArrayList<>(judgesOrigin);
        List<TeamDTO> teams = new ArrayList<>(teamsOrigin);
        teams.sort(Comparator.comparing(TeamDTO::getTeamPoints).reversed());
        Collections.shuffle(judges);
        List<List<TeamDTO>> list = brakeToTeamsOf4(teams);
        List<List<TeamDTO>> teamBraked = new ArrayList<>();
        list.forEach(e -> {
            ArrayList<TeamDTO> teamDTOS = new ArrayList<>(e);
            Collections.shuffle(teamDTOS);
            teamBraked.add(teamDTOS);
        });
        for (int i = 0; i < roomDetails.size(); i++) {
            List<RoomPositionDTO> positions = new ArrayList<>();
            for (int j = 0; j < teamBraked.get(i).size(); j++) {
                positions.add(RoomPositionDTO.builder()
                        .roomDetailsId(roomDetails.get(i).getId())
                        .position(POSITION.fromInt(j + 1))
                        .team(teamBraked.get(i).get(j))
                        .build());
            }
            roomDetails.get(i).setJudges(List.of(judges.get(i)));
            roomDetails.get(i).setPositions(positions);
            roomDetails.get(i).setRoomName(String.valueOf(i + 1));
        }

        return roomDetails;
    }


    //TODO looks like i will need quite many refactor here
//    public RoundTableDTO generateRoundTable(Long tournamentId) {
//        Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow(() -> new CustomException("Tournament not found"));
//        List<RoomDetailsDTO> newRoundRooms;
//        Integer roomAmount = tournament.getTeams().size() % 4 == 0 ? tournament.getTeams().size() / 4 : tournament.getTeams().size() / 4 + 1;
//        Integer roundNumber = tournament.getRoomDetails().size() == 0|| tournament.getRoomDetails() == null  ? 1 : tournament.getRoomDetails().stream().map(RoomDetails::getRoundNumber).max(Integer::compareTo).get() + 1;
//        boolean firstRoundInProgress = tournament.getRoomDetails().size() == 0|| tournament.getRoomDetails() == null ? false :
//                tournament.getRoomDetails().stream()
//                        .filter(e -> e.getRoundNumber() == 1)
//                        .findFirst().get()
//                        .getRoundStatus().equals(ROUND_STATUS.IN_PROGRESS);
//        newRoundRooms = roomService.createRoomDetailsTamplate(tournament, roomAmount, roundNumber);
//        if(roundNumber == 1 && !firstRoundInProgress){
//            newRoundRooms = roomService.getAllRoomDetails();
//        }
//        else if (roundNumber == 1 && !firstRoundInProgress) {
//            newRoundRooms = roomService.createAll(randomlyGenerateRooms(newRoundRooms,
//                    tournament.getJudges().stream()
//                            .map(userConverter::toDTO).collect(Collectors.toList()),
//                    tournament.getTeams().stream()
//                            .map(teamConverter::toTeamDTO).collect(Collectors.toList())));
//        } else {
//
//            newRoundRooms = roomService.createAll(generateRoomsByScore(
//                    tournament.getRoomDetails().stream()
//                            .map(roomDetailsConverter::toDTO).collect(Collectors.toList()),
//                    newRoundRooms,
//                    tournament.getJudges().stream()
//                            .map(userConverter::toDTO).collect(Collectors.toList()),
//                    tournament.getTeams().stream()
//                            .map(teamConverter::toTeamDTO).collect(Collectors.toList())));
//        }
//
//        return roomDetailsConverter.toTable(newRoundRooms);
//
//    }
//
//
//    //TODO looks like i will need quite many refactor here
//    public RoundTableDTO generate(Long tournamentId) {
//        Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow(() -> new CustomException("Tournament not found"));
//        List<RoomDetailsDTO> newRoundRooms;
//        Integer roomAmount = tournament.getTeams().size() % 4 == 0 ? tournament.getTeams().size() / 4 : tournament.getTeams().size() / 4 + 1;
//        Integer roundNumber = tournament.getRoomDetails().size() == 0 || tournament.getRoomDetails() == null ? 1 : tournament.getRoomDetails().stream().map(RoomDetails::getRoundNumber).max(Integer::compareTo).get() + 1;
//        boolean firstRoundInProgress = tournament.getRoomDetails().size() == 0 || tournament.getRoomDetails() == null ? false :
//                tournament.getRoomDetails().stream()
//                        .filter(e -> e.getRoundNumber() == 1)
//                        .findFirst().get()
//                        .getRoundStatus().equals(ROUND_STATUS.IN_PROGRESS);
//
//
//        newRoundRooms = roomService.createRoomDetailsTamplate(tournament, roomAmount, roundNumber);
//        if (roundNumber == 1 && !firstRoundInProgress) {
//            newRoundRooms = roomService.getAllRoomDetails();
//        } else if (roundNumber == 1 && !firstRoundInProgress) {
//            newRoundRooms = roomService.createAll(randomlyGenerateRooms(newRoundRooms,
//                    tournament.getJudges().stream()
//                            .map(userConverter::toDTO).collect(Collectors.toList()),
//                    tournament.getTeams().stream()
//                            .map(teamConverter::toTeamDTO).collect(Collectors.toList())));
//        } else {
//
//            newRoundRooms = roomService.createAll(generateRoomsByScore(
//                    tournament.getRoomDetails().stream()
//                            .map(roomDetailsConverter::toDTO).collect(Collectors.toList()),
//                    newRoundRooms,
//                    tournament.getJudges().stream()
//                            .map(userConverter::toDTO).collect(Collectors.toList()),
//                    tournament.getTeams().stream()
//                            .map(teamConverter::toTeamDTO).collect(Collectors.toList())));
//        }
//
//        return roomDetailsConverter.toTable(newRoundRooms);
//
//    }


    public RoundTableDTO generateRoundTable(Long tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow(() -> new CustomException("Tournament not found"));
        List<RoomDetailsDTO> newRoundRooms;
        Integer roomAmount = tournament.getTeams().size() % 4 == 0 ? tournament.getTeams().size() / 4 : tournament.getTeams().size() / 4 + 1;
        Integer roundNumber = tournament.getRoomDetails().isEmpty() ? 1 : tournament.getRoomDetails().stream().map(RoomDetails::getRoundNumber).max(Integer::compareTo).get() + 1;

        //Check if we can create new round
        tournament.getRoomDetails().stream().filter(e -> e.getRoundNumber() == roundNumber - 1).forEach(e -> {
            if (e.getRoundStatus().equals(ROUND_STATUS.IN_PROGRESS)) {
                throw new CustomException("Cant start new round, cause previous round still in progress Judge:" + e.getJudges().toString());
            }
        });

        newRoundRooms = roomService.createRoomDetailsTamplate(tournament, roomAmount, roundNumber);
        if (roundNumber == 1) {
            newRoundRooms = roomService.createAll(randomlyGenerateRooms(newRoundRooms,
                    tournament.getJudges().stream()
                            .map(userConverter::toDTO).collect(Collectors.toList()),
                    tournament.getTeams().stream()
                            .map(teamConverter::toTeamDTO).collect(Collectors.toList())));

        } else {
            newRoundRooms = roomService.createAll(generateRoomsByScore(
                    tournament.getRoomDetails().stream()
                            .map(roomDetailsConverter::toDTO).collect(Collectors.toList()),
                    newRoundRooms,
                    tournament.getJudges().stream()
                            .map(userConverter::toDTO).collect(Collectors.toList()),
                    tournament.getTeams().stream()
                            .map(teamConverter::toTeamDTO).collect(Collectors.toList())));
        }
        tournament.setTournamentStatus(TOURNAMENT_STATUS.IN_PROGRESS);
        updateTournament(tournamentConverter.toDTO(tournament));
        return roomDetailsConverter.toTable(newRoundRooms);

    }

    public RoundTableDTO getRoundTable(Long tournamentId, int roundNumber) {
        Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow(() -> new CustomException("Tournament not found"));
        List<RoomDetailsDTO> roundRooms = tournament.getRoomDetails()
                .stream()
                .filter(e -> e.getRoundNumber() == roundNumber)
                .toList()
                .stream()
                .map(roomDetailsConverter::toDTO).toList();

        if (roundRooms.isEmpty()) {
            throw new CustomException("Cant find any rooms in tournament");
        }

        return roomDetailsConverter.toTable(roundRooms);
    }
}

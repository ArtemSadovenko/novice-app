package com.novice.debatenovice.service;

import com.novice.debatenovice.converter.RoundResultsConverter;
import com.novice.debatenovice.dto.RoundResultsDTO;
import com.novice.debatenovice.exeptions.CustomException;
import com.novice.debatenovice.repository.RoundResultsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoundResultsService {

    @Autowired
    private RoundResultsRepository roundResultsRepository;

    @Autowired
    private RoundResultsConverter roundResultsConverter;


    public RoundResultsDTO create(RoundResultsDTO roundResults) {
        return roundResultsConverter.convertRoundResultsToDTO(roundResultsRepository.save(roundResultsConverter.convertDTOToRoundResults(roundResults)));
    }

    public List<RoundResultsDTO> findAll() {
        return roundResultsRepository.findAll().stream().map(e -> roundResultsConverter.convertRoundResultsToDTO(e)).collect(Collectors.toList());
    }

    public RoundResultsDTO findById(Long id) {
        return roundResultsConverter.convertRoundResultsToDTO(roundResultsRepository.findById(id).orElseThrow(() -> new CustomException("Round results not found with id: " + id)));
    }

    public RoundResultsDTO update(RoundResultsDTO roundResults) {
        return roundResultsConverter.convertRoundResultsToDTO(roundResultsRepository.save(roundResultsConverter.convertDTOToRoundResults(roundResults)));
    }

    public List<RoundResultsDTO> createAll(List<RoundResultsDTO> roundResults) {
        return roundResultsRepository.saveAll(roundResults.stream().map(roundResultsConverter::convertDTOToRoundResults).toList()).stream().map(roundResultsConverter::convertRoundResultsToDTO).collect(Collectors.toList());
    }

    public void delete(Long id) {
        roundResultsRepository.deleteById(id);
    }
}

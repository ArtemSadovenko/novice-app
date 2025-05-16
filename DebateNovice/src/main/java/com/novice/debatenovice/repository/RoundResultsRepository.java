package com.novice.debatenovice.repository;

import com.novice.debatenovice.entity.RoundResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundResultsRepository extends JpaRepository<RoundResults, Long> {
}

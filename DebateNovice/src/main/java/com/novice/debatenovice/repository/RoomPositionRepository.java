package com.novice.debatenovice.repository;

import com.novice.debatenovice.entity.RoomPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomPositionRepository extends JpaRepository<RoomPosition, Long> {
}

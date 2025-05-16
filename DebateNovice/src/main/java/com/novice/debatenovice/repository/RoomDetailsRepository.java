package com.novice.debatenovice.repository;

import com.novice.debatenovice.entity.RoomDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomDetailsRepository extends JpaRepository<RoomDetails, Long> {
}

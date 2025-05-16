package com.novice.debatenovice.entity;

import com.novice.debatenovice.enums.PLACE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "round_results")
public class RoundResults {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PLACE place;

    @ElementCollection
    @CollectionTable(name = "speaker_points", joinColumns = @JoinColumn(name = "result_id"))
    @MapKeyColumn(name = "user_id")
    @Column(name = "points")
    private Map<Long, Integer> speakerPoints; //<UserId, speaker point>

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_details_id")
    private RoomDetails roomDetails;
}


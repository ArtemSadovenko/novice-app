package com.novice.debatenovice.entity;

import com.novice.debatenovice.enums.ROUND_STATUS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "room_details")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RoomDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //This is what I need in tableDTO

    @Column(name = "round_number")
    private Integer roundNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "round_status")
    private ROUND_STATUS roundStatus;


    @Column
    private String roomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    //TODO add feedback

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "room_judges",
            joinColumns = @JoinColumn(name = "room_details_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> judges;

    @OneToMany(mappedBy = "roomDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoomPosition> positions;

    @OneToMany(mappedBy = "roomDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoundResults> results;

    //todo add room-link(if online use google calendar api)
}


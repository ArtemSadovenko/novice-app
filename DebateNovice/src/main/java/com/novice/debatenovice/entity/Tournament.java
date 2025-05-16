package com.novice.debatenovice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.novice.debatenovice.enums.TOURNAMENT_STATUS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "tournaments")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tournament_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @ElementCollection
    @CollectionTable(name = "tournament_topics", joinColumns = @JoinColumn(name = "tournament_id"))
    @MapKeyColumn(name = "round_number")
    @Column(name = "topic")
    private Map<String, String> topics;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Team> teams;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tournament_judges",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private List<User> judges;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<RoomDetails> roomDetails;

    @Enumerated(EnumType.STRING)
    @Column(name = "tournament_status", nullable = false)
    private TOURNAMENT_STATUS tournamentStatus;

    //TODO date of creation, date of event, logo, location, format, teb-link(online), creator
}
package com.novice.debatenovice.entity;

import com.novice.debatenovice.enums.USER_EXPERIENCE;
import com.novice.debatenovice.enums.USER_ROLE;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Set<USER_ROLE> roles;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "experience", nullable = false)
    private USER_EXPERIENCE experience;

    @Column(name = "average_score")
    private Double averageScore;

//    @Column(name = "created_at", nullable = false, updatable = false)
//    private LocalDateTime createdAt;


//    @PrePersist
//    public void prePersist() {
//        this.createdAt = LocalDateTime.now();  // Set the current time
//    }
}
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enseignants")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enseignant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(length = 100)
    private String specialite;

    @Column(name = "heures_enseignees")
    @Builder.Default
    private Integer heuresEnseignees = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutEnseignant statut;

    public enum StatutEnseignant {
        PERMANENT,
        VACATAIRE
    }
}

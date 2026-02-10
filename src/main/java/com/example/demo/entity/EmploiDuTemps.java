package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "emplois_du_temps", indexes = {
    @Index(name = "idx_edt_date_debut", columnList = "date_debut"),
    @Index(name = "idx_edt_date_fin", columnList = "date_fin"),
    @Index(name = "idx_edt_enseignant", columnList = "enseignant_id"),
    @Index(name = "idx_edt_salle", columnList = "salle_id")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmploiDuTemps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enseignant_id", nullable = false)
    private Enseignant enseignant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salle_id", nullable = false)
    private Salle salle;

    @Column(name = "date_debut", nullable = false)
    private LocalDateTime dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDateTime dateFin;

    @Column(length = 50)
    private String recurrence;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatutEmploiDuTemps statut = StatutEmploiDuTemps.PLANNIFIE;

    @OneToMany(mappedBy = "emploiDuTemps", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Presence> presences = new ArrayList<>();

    public enum StatutEmploiDuTemps {
        PLANNIFIE,
        ANNULE
    }
}

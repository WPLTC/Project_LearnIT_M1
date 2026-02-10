package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "notes", indexes = {
    @Index(name = "idx_note_etudiant", columnList = "etudiant_id"),
    @Index(name = "idx_note_evaluation", columnList = "evaluation_id")
}, uniqueConstraints = {
    @UniqueConstraint(name = "uk_note_etudiant_evaluation", columnNames = {"etudiant_id", "evaluation_id"})
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_id", nullable = false)
    private Evaluation evaluation;

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal valeur;

    @Column(columnDefinition = "TEXT")
    private String commentaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "saisie_par", nullable = false)
    private Enseignant saisiePar;
}

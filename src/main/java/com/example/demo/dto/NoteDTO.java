package com.example.demo.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {

    private Long id;

    @NotNull(message = "L'étudiant est obligatoire")
    private Long etudiantId;

    private EtudiantDTO etudiant;

    @NotNull(message = "L'évaluation est obligatoire")
    private Long evaluationId;

    private EvaluationDTO evaluation;

    @NotNull(message = "La valeur de la note est obligatoire")
    @DecimalMin(value = "0.00", message = "La note doit être entre 0 et 20")
    @DecimalMax(value = "20.00", message = "La note doit être entre 0 et 20")
    private BigDecimal valeur;

    private String commentaire;

    @NotNull(message = "L'enseignant saisissant la note est obligatoire")
    private Long saisieParId;

    private EnseignantDTO saisiePar;
}

package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmploiDuTempsDTO {

    private Long id;

    @NotNull(message = "Le module est obligatoire")
    private Long moduleId;

    private ModuleDTO module;

    @NotNull(message = "L'enseignant est obligatoire")
    private Long enseignantId;

    private EnseignantDTO enseignant;

    @NotNull(message = "La salle est obligatoire")
    private Long salleId;

    private SalleDTO salle;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDateTime dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDateTime dateFin;

    @Size(max = 50, message = "La récurrence ne doit pas dépasser 50 caractères")
    private String recurrence;

    @NotNull(message = "Le statut est obligatoire")
    private StatutEmploiDuTemps statut;

    public enum StatutEmploiDuTemps {
        PLANNIFIE,
        ANNULE
    }
}

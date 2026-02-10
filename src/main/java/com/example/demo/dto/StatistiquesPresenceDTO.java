package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO pour les statistiques de présence d'un étudiant ou d'une classe
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatistiquesPresenceDTO {

    private Long userId;
    private String nomComplet;

    private Long classeId;
    private String nomClasse;

    private Integer totalSessions;
    private Integer sessionsPresent;
    private Integer sessionsAbsent;
    private Integer sessionsJustifiees;

    private BigDecimal tauxPresence;
    private BigDecimal heuresAbsence;
}

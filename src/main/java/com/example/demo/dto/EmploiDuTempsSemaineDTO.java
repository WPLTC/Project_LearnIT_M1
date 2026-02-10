package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO pour l'emploi du temps hebdomadaire ou journalier
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmploiDuTempsSemaineDTO {

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Long classeId;
    private String nomClasse;
    private Long enseignantId;
    private String nomEnseignant;

    private List<JourDTO> jours;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JourDTO {
        private LocalDate date;
        private String jourSemaine;
        private List<SessionDTO> sessions;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SessionDTO {
        private Long id;
        private String moduleNom;
        private String enseignantNom;
        private String salleNom;
        private LocalDateTime heureDebut;
        private LocalDateTime heureFin;
        private String statut;
    }
}

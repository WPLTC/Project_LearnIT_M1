package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO pour les disponibilités des salles
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisponibiliteSalleDTO {

    private Long salleId;
    private String salleNom;
    private Integer capacite;
    private String equipements;
    private String localisation;

    private List<CreneauDTO> creneauxOccupes;
    private List<CreneauDTO> creneauxDisponibles;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreneauDTO {
        private LocalDateTime debut;
        private LocalDateTime fin;
        private String motif;
        private String type;
    }
}

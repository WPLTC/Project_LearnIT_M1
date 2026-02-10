package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO pour le relevé de notes d'un étudiant
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReleveNotesDTO {

    private EtudiantDTO etudiant;
    private ClasseDTO classe;
    private List<ModuleNotesDTO> modulesNotes;
    private BigDecimal moyenneGenerale;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModuleNotesDTO {
        private Long moduleId;
        private String moduleNom;
        private Integer credits;
        private List<NoteDetailDTO> notes;
        private BigDecimal moyenneModule;
        private String mention;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NoteDetailDTO {
        private Long evaluationId;
        private String evaluationNom;
        private String typeEvaluation;
        private BigDecimal coefficient;
        private BigDecimal valeur;
    }
}

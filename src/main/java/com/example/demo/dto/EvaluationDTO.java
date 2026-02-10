package com.example.demo.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationDTO {

    private Long id;

    @NotNull(message = "Le module est obligatoire")
    private Long moduleId;

    private ModuleDTO module;

    @NotBlank(message = "Le nom de l'évaluation est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    private String nom;

    @NotNull(message = "Le type d'évaluation est obligatoire")
    private TypeEvaluation type;

    @NotNull(message = "Le coefficient est obligatoire")
    @DecimalMin(value = "0.00", message = "Le coefficient doit être entre 0 et 1")
    @DecimalMax(value = "1.00", message = "Le coefficient doit être entre 0 et 1")
    private BigDecimal coefficient;

    private LocalDate date;

    public enum TypeEvaluation {
        DEVOIR,
        EXAM
    }
}

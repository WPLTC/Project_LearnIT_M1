package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleDTO {

    private Long id;

    @NotBlank(message = "Le nom du module est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    private String nom;

    private String description;

    @Min(value = 1, message = "Le nombre de crédits doit être au moins de 1")
    private Integer credits;

    private Long classeId;

    private ClasseDTO classe;

    private Long enseignantId;

    private EnseignantDTO enseignant;
}

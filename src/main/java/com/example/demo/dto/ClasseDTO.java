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
public class ClasseDTO {

    private Long id;

    @NotBlank(message = "Le nom de la classe est obligatoire")
    @Size(max = 50, message = "Le nom ne doit pas dépasser 50 caractères")
    private String nom;

    @Size(max = 20, message = "Le niveau ne doit pas dépasser 20 caractères")
    private String niveau;

    @Min(value = 1, message = "La capacité doit être au moins de 1")
    private Integer capacite;

    @Size(max = 100, message = "La filière ne doit pas dépasser 100 caractères")
    private String filiere;
}

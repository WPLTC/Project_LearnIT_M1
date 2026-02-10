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
public class SalleDTO {

    private Long id;

    @NotBlank(message = "Le nom de la salle est obligatoire")
    @Size(max = 50, message = "Le nom ne doit pas dépasser 50 caractères")
    private String nom;

    @Min(value = 1, message = "La capacité doit être au moins de 1")
    private Integer capacite;

    @Size(max = 255, message = "Les équipements ne doivent pas dépasser 255 caractères")
    private String equipements;

    @Size(max = 100, message = "La localisation ne doit pas dépasser 100 caractères")
    private String localisation;
}

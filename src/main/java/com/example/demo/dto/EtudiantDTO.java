package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EtudiantDTO {

    private Long id;

    @NotNull(message = "L'utilisateur associé est obligatoire")
    private Long userId;

    private UserDTO user;

    @NotBlank(message = "Le matricule est obligatoire")
    @Size(max = 20, message = "Le matricule ne doit pas dépasser 20 caractères")
    private String matricule;

    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate dateNaissance;

    private Long classeId;

    private ClasseDTO classe;
}

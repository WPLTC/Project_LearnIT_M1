package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterTeacherDTO {
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;
    @NotBlank
    @Size(min = 8)
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private String nom;
    @NotBlank
    private String prenom;

    private String specialite;
    private Integer heuresEnseignees;
    @NotNull
    private EnseignantDTO.StatutEnseignant statut;
}

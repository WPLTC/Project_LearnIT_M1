package com.example.demo.dto;

import jakarta.validation.constraints.Min;
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
public class EnseignantDTO {

    private Long id;

    @NotNull(message = "L'utilisateur associé est obligatoire")
    private Long userId;

    private UserDTO user;

    @Size(max = 100, message = "La spécialité ne doit pas dépasser 100 caractères")
    private String specialite;

    @Min(value = 0, message = "Les heures enseignées ne peuvent pas être négatives")
    private Integer heuresEnseignees;

    @NotNull(message = "Le statut est obligatoire")
    private StatutEnseignant statut;

    public enum StatutEnseignant {
        PERMANENT,
        VACATAIRE
    }
}

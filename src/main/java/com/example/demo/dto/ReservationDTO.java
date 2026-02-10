package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private Long id;

    @NotNull(message = "L'utilisateur est obligatoire")
    private Long userId;

    private UserDTO user;

    @NotNull(message = "La salle est obligatoire")
    private Long salleId;

    private SalleDTO salle;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDateTime dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDateTime dateFin;

    @Size(max = 255, message = "Le motif ne doit pas dépasser 255 caractères")
    private String motif;

    @NotNull(message = "Le statut est obligatoire")
    private StatutReservation statut;

    private Long validatedBy;

    private UserDTO validateur;

    public enum StatutReservation {
        EN_ATTENTE,
        VALIDEE,
        ANNULEE
    }
}

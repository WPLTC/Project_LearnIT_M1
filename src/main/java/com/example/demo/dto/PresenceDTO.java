package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PresenceDTO {

    private Long id;

    @NotNull(message = "L'emploi du temps est obligatoire")
    private Long emploiDuTempsId;

    private EmploiDuTempsDTO emploiDuTemps;

    @NotNull(message = "L'utilisateur est obligatoire")
    private Long userId;

    private UserDTO user;

    @Builder.Default
    private Boolean present = true;

    private String justification;

    private Long marqueParId;

    private UserDTO marquePar;
}

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

    @NotNull(message = "L'utilisateur associe est obligatoire")
    private Long userId;

    private UserDTO user;

    @Size(max = 100, message = "La specialite ne doit pas depasser 100 caracteres")
    private String specialite;

    @Min(value = 0, message = "Les heures enseignees ne peuvent pas etre negatives")
    private Integer heuresEnseignees;

    @NotNull(message = "Le statut est obligatoire")
    private StatutEnseignant statut;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }
    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }
    public Integer getHeuresEnseignees() { return heuresEnseignees; }
    public void setHeuresEnseignees(Integer heuresEnseignees) { this.heuresEnseignees = heuresEnseignees; }
    public StatutEnseignant getStatut() { return statut; }
    public void setStatut(StatutEnseignant statut) { this.statut = statut; }

    public enum StatutEnseignant {
        PERMANENT,
        VACATAIRE
    }
}

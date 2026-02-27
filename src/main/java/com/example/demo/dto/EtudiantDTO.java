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

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 50, message = "Le nom ne doit pas depasser 50 caracteres")
    private String nom;

    @NotBlank(message = "Le prenom est obligatoire")
    @Size(max = 50, message = "Le prenom ne doit pas depasser 50 caracteres")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Size(max = 100, message = "L'email ne doit pas depasser 100 caracteres")
    private String email;

    @NotNull(message = "L'utilisateur associe est obligatoire")
    private Long userId;

    private UserDTO user;

    @NotBlank(message = "Le matricule est obligatoire")
    @Size(max = 20, message = "Le matricule ne doit pas depasser 20 caracteres")
    private String matricule;

    @Past(message = "La date de naissance doit etre dans le passe")
    private LocalDate dateNaissance;

    private Long classeId;

    private ClasseDTO classe;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }
    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }
    public Long getClasseId() { return classeId; }
    public void setClasseId(Long classeId) { this.classeId = classeId; }
    public ClasseDTO getClasse() { return classe; }
    public void setClasse(ClasseDTO classe) { this.classe = classe; }
}

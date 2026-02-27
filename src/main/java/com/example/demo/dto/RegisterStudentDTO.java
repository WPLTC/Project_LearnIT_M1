package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class RegisterStudentDTO {
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;
    @NotBlank
    @Size(min = 8)
    private String password;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String nom;
    @NotBlank
    private String prenom;

    @NotBlank
    private String matricule;

    @Past
    private LocalDate dateNaissance;

    private Long classeId;
}

package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
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
public class AuditLogDTO {

    private Long id;

    @NotNull(message = "L'utilisateur est obligatoire")
    private Long userId;

    private UserDTO user;

    @NotBlank(message = "L'action est obligatoire")
    @Size(max = 100, message = "L'action ne doit pas dépasser 100 caractères")
    private String action;

    private Long entityId;


    private LocalDateTime timestamp;

    private String details;
}

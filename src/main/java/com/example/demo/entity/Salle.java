package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "salles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nom;

    private Integer capacite;

    @Column(length = 255)
    private String equipements;

    @Column(length = 100)
    private String localisation;

    @OneToMany(mappedBy = "salle", cascade = CascadeType.ALL)
    @Builder.Default
    private List<EmploiDuTemps> emploisDuTemps = new ArrayList<>();

    @OneToMany(mappedBy = "salle", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Reservation> reservations = new ArrayList<>();
}

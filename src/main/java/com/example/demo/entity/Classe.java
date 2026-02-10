package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "classes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nom;

    @Column(length = 20)
    private String niveau;

    private Integer capacite;

    @Column(length = 100)
    private String filiere;

    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Etudiant> etudiants = new ArrayList<>();

    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Module> modules = new ArrayList<>();
}

package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enseignants")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enseignant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(length = 100)
    private String specialite;

    @Column(name = "heures_enseignees")
    @Builder.Default
    private Integer heuresEnseignees = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutEnseignant statut;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
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

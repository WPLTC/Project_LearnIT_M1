package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "presences", indexes = {
    @Index(name = "idx_presence_edt", columnList = "emploi_du_temps_id"),
    @Index(name = "idx_presence_user", columnList = "user_id")
}, uniqueConstraints = {
    @UniqueConstraint(name = "uk_presence_edt_user", columnNames = {"emploi_du_temps_id", "user_id"})
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Presence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emploi_du_temps_id", nullable = false)
    private EmploiDuTemps emploiDuTemps;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Builder.Default
    private Boolean present = true;

    @Column(columnDefinition = "TEXT")
    private String justification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marque_par")
    private User marquePar;
}

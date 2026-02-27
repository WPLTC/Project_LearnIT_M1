package com.example.demo.config;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Profile("init")
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final SalleRepository salleRepository;
    private final EnseignantRepository enseignantRepository;
    private final ModuleRepository moduleRepository;
    private final EmploiDuTempsRepository emploiDuTempsRepository;
    private final ReservationRepository reservationRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           SalleRepository salleRepository,
                           EnseignantRepository enseignantRepository,
                           ModuleRepository moduleRepository,
                           EmploiDuTempsRepository emploiDuTempsRepository,
                           ReservationRepository reservationRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.salleRepository = salleRepository;
        this.enseignantRepository = enseignantRepository;
        this.moduleRepository = moduleRepository;
        this.emploiDuTempsRepository = emploiDuTempsRepository;
        this.reservationRepository = reservationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // retry loop: wait for Hibernate to finish schema creation before inserting
        int attempts = 0;
        final int maxAttempts = 10;
        while (attempts < maxAttempts) {
            try {
                if (userRepository.count() == 0) {
                    User admin = User.builder()
                            .username("admin")
                            .password(passwordEncoder.encode("adminpass"))
                            .email("admin@example.com")
                            .nom("Admin")
                            .prenom("Super")
                            .role(User.RoleEnum.ADMIN)
                            .build();
                    userRepository.save(admin);

                    User enseignantUser = User.builder()
                            .username("enseignant1")
                            .password(passwordEncoder.encode("enseignantpass"))
                            .email("ens1@example.com")
                            .nom("Dupont")
                            .prenom("Jean")
                            .role(User.RoleEnum.ENSEIGNANT)
                            .build();
                    enseignantUser = userRepository.save(enseignantUser);

                    User etudiantUser = User.builder()
                            .username("etudiant1")
                            .password(passwordEncoder.encode("etudiantpass"))
                            .email("etud1@example.com")
                            .nom("Martin")
                            .prenom("Claire")
                            .role(User.RoleEnum.ETUDIANT)
                            .build();
                    etudiantUser = userRepository.save(etudiantUser);

                    // create enseignant entity linked to user
                    Enseignant enseignant = new Enseignant();
                    enseignant.setUser(enseignantUser);
                    enseignant.setSpecialite("Informatique");
                    enseignant.setHeuresEnseignees(120);
                    enseignant.setStatut(Enseignant.StatutEnseignant.PERMANENT);
                    enseignant = enseignantRepository.save(enseignant);

                    // create salle
                    Salle salleA = new Salle();
                    salleA.setNom("Amphi A");
                    salleA.setCapacite(120);
                    salleA.setEquipements("Projecteur, Son");
                    salleA.setLocalisation("Bâtiment 1");
                    salleA = salleRepository.save(salleA);

                    Salle salleB = new Salle();
                    salleB.setNom("Salle B101");
                    salleB.setCapacite(40);
                    salleB.setEquipements("Tableau, PC");
                    salleB.setLocalisation("Bâtiment 2");
                    salleB = salleRepository.save(salleB);

                    // create module
                    com.example.demo.entity.Module module = new com.example.demo.entity.Module();
                    module.setNom("Programmation Java");
                    module.setDescription("Introduction à Java et Spring Boot");
                    module.setCredits(6);
                    module.setEnseignant(enseignant);
                    module = moduleRepository.save(module);

                    // create emploi du temps
                    EmploiDuTemps edt = new EmploiDuTemps();
                    edt.setModule(module);
                    edt.setEnseignant(enseignant);
                    edt.setSalle(salleA);
                    edt.setDateDebut(LocalDateTime.of(2026,3,1,9,0));
                    edt.setDateFin(LocalDateTime.of(2026,3,1,11,0));
                    edt.setRecurrence("NONE");
                    edt.setStatut(EmploiDuTemps.StatutEmploiDuTemps.PLANNIFIE);
                    edt = emploiDuTempsRepository.save(edt);

                    // create reservation by etudiant in salleB (non-conflicting)
                    Reservation res = new Reservation();
                    res.setUser(etudiantUser);
                    res.setSalle(salleB);
                    res.setDateDebut(LocalDateTime.of(2026,3,2,14,0));
                    res.setDateFin(LocalDateTime.of(2026,3,2,16,0));
                    res.setMotif("Réunion projet");
                    res.setStatut(Reservation.StatutReservation.VALIDEE);
                    reservationRepository.save(res);
                }
                // success or nothing to do
                break;
            } catch (Exception ex) {
                attempts++;
                System.out.println("DataInitializer: database not ready, retrying (" + attempts + "/" + maxAttempts + ")...");
                Thread.sleep(2000);
                if (attempts >= maxAttempts) {
                    System.err.println("DataInitializer: giving up after " + attempts + " attempts: " + ex.getMessage());
                    throw ex;
                }
            }
        }
    }
}

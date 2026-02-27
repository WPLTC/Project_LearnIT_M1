-- seed_data.sql
-- Script d'insertion pour PostgreSQL
-- Remplacez les placeholders <HASH_ADMIN>, <HASH_ENSEIGNANT>, <HASH_ETUDIANT> par des hash BCrypt valides

-- Users
INSERT INTO users (username, password, email, nom, prenom, role)
VALUES
('admin', '$2a$10$rsZphyHUUrG4AygeaUP6MeROO0uloibUtOQzikZllBPdiy3CNTJHK', 'admin@example.com', 'Admin', 'Super', 'ADMIN'),
('enseignant1', '$2a$10$37YYqYKQRXmaIbQy0YG/3.dYR9iz5kHO20oErrkeGQrAaVcPeccZ2', 'ens1@example.com', 'Dupont', 'Jean', 'ENSEIGNANT'),
('etudiant1', '$2a$10$Ef.FOUAm6XotAkG5IplAtuHUNQ01.xil7fCltg8N37ceHbfpKRJ4m ', 'etud1@example.com', 'Martin', 'Claire', 'ETUDIANT');

-- Salles
INSERT INTO salles (nom, capacite, equipements, localisation)
VALUES
('Amphi A', 120, 'Projecteur, Son', 'Bâtiment 1'),
('Salle B101', 40, 'Tableau, PC', 'Bâtiment 2');

-- Enseignant (lié à user enseignant1)
INSERT INTO enseignants (user_id, specialite, heures_enseignees, statut)
VALUES
((SELECT id FROM users WHERE username='enseignant1'), 'Informatique', 120, 'PERMANENT');

-- Module (lié à l'enseignant)
INSERT INTO modules (nom, description, credits, enseignant_id)
VALUES
('Programmation Java', 'Introduction à Java et Spring Boot', 6, (SELECT id FROM enseignants WHERE user_id = (SELECT id FROM users WHERE username='enseignant1')));

-- Emploi du temps (lié au module, enseignant et salle)
INSERT INTO emplois_du_temps (module_id, enseignant_id, salle_id, date_debut, date_fin, recurrence, statut)
VALUES
((SELECT id FROM modules WHERE nom='Programmation Java'),
 (SELECT id FROM enseignants WHERE user_id = (SELECT id FROM users WHERE username='enseignant1')),
 (SELECT id FROM salles WHERE nom='Amphi A'),
 '2026-03-01 09:00:00', '2026-03-01 11:00:00', 'NONE', 'PLANNIFIE');

-- Reservation (par etudiant1 dans Salle B101)
INSERT INTO reservations (user_id, salle_id, date_debut, date_fin, motif, statut)
VALUES
((SELECT id FROM users WHERE username='etudiant1'), (SELECT id FROM salles WHERE nom='Salle B101'), '2026-03-02 14:00:00', '2026-03-02 16:00:00', 'Réunion projet', 'VALIDEE');

-- FIN du script

-- Instructions :
-- 1) Générez les hash BCrypt pour les mots de passe souhaités (ex: adminpass, enseignantpass, etudiantpass) en utilisant l'utilitaire Java que j'ai ajouté (voir README ci-dessous),
--    ou utilisez un outil externe. Ensuite remplacez <HASH_ADMIN>, <HASH_ENSEIGNANT>, <HASH_ETUDIANT> par les hash produits.
-- 2) Exécutez le script :
--    psql -h localhost -U <dbuser> -d <dbname> -f seed_data.sql
--    (ou utilisez PgAdmin pour exécuter le fichier SQL)

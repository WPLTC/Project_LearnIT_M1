-- create_schema.sql
-- Minimal schema to allow seed inserts

CREATE TABLE IF NOT EXISTS users (
  id BIGSERIAL PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  nom VARCHAR(100) NOT NULL,
  prenom VARCHAR(100) NOT NULL,
  role VARCHAR(50) NOT NULL,
  created_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS salles (
  id BIGSERIAL PRIMARY KEY,
  nom VARCHAR(100) NOT NULL,
  capacite INTEGER,
  equipements TEXT,
  localisation VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS enseignants (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT,
  specialite VARCHAR(255),
  heures_enseignees INTEGER,
  statut VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS modules (
  id BIGSERIAL PRIMARY KEY,
  nom VARCHAR(255),
  description TEXT,
  credits INTEGER,
  enseignant_id BIGINT
);

CREATE TABLE IF NOT EXISTS emplois_du_temps (
  id BIGSERIAL PRIMARY KEY,
  module_id BIGINT,
  enseignant_id BIGINT,
  salle_id BIGINT,
  date_debut TIMESTAMP,
  date_fin TIMESTAMP,
  recurrence VARCHAR(50),
  statut VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS reservations (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT,
  salle_id BIGINT,
  date_debut TIMESTAMP,
  date_fin TIMESTAMP,
  motif TEXT,
  statut VARCHAR(50),
  validated_by BIGINT
);

-- indexes to match JPA expectations
CREATE INDEX IF NOT EXISTS idx_user_username ON users (username);
CREATE INDEX IF NOT EXISTS idx_user_email ON users (email);
CREATE INDEX IF NOT EXISTS idx_user_role ON users (role);

COMMIT;

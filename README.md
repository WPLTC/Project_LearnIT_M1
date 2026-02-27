**Project Overview**
- **Name:** LearnIT (Spring Boot backend)
- **Description:** API de gestion d'établissement (authentification JWT, gestion des salles, emplois du temps, réservations, présences, modules, étudiants, enseignants, classes, évaluations).

**Requirements**
- **Java:** 17+ (JDK tested: 23 runtime used locally)
- **Maven:** 3.x
- **Database:** PostgreSQL (local)

**Quick Start (build & run)**
- Build with Maven:

```bash
mvn clean package -DskipTests
```

- Run the packaged JAR (profile `init` seeds DB on startup if configured):

```powershell
# on Windows PowerShell
$env:DB_USER='postgres'; $env:DB_PASS='admin'; java -jar target/demo-0.0.1-SNAPSHOT.jar --spring.profiles.active=init
```

- Or run from Maven (dev):

```bash
mvn spring-boot:run
```

**Environment variables / Configuration**
- **DB_USER**: PostgreSQL user (default in `application.properties` is `postgres`)
- **DB_PASS**: PostgreSQL password (set before running)
- **APP_JWT_SECRET** or `app.jwt.secret` in `application.properties`: secret JWT (change in production)
- **server.port**: configured in `application.properties` (default 8081)

Files of interest:
- Postman collection: [postman_collection.json](postman_collection.json)
- DB schema SQL: [create_schema.sql](create_schema.sql)
- DB seed SQL: [seed_data_filled.sql](seed_data_filled.sql)
- App properties: [src/main/resources/application.properties](src/main/resources/application.properties)
- Build config: [pom.xml](pom.xml)

**Database seeding**
If your DB user cannot create the schema, run the SQL files as a superuser (example):

```bash
psql -h localhost -U postgres -d gestion_etablissement -f create_schema.sql
psql -h localhost -U postgres -d gestion_etablissement -f seed_data_filled.sql
# then (optional) chown to your app role
psql -h localhost -U postgres -d gestion_etablissement -c "ALTER TABLE public.users OWNER TO ipf;"
```

**Main API Routes (summary)**
- **Auth**
  - POST /api/auth/login : connexion (body {email, password}) → renvoie token
  - POST /api/auth/register : création d'utilisateur
  - GET  /api/auth/me : informations utilisateur (Authorization: Bearer <token>)

- **Emplois du temps**
  - GET  /api/emplois-du-temps
  - POST /api/emplois-du-temps/planifier : planifier un cours

- **Reservations**
  - GET  /api/reservations
  - GET  /api/reservations/disponibilites?salleId={id}
  - POST /api/reservations : créer une réservation

- **Présences**
  - POST /api/presences/marquer : marquer présence

- **Ressources**
  - GET /api/modules
  - GET /api/etudiants
  - GET /api/enseignants
  - GET /api/classes
  - GET /api/evaluations

(Remarque: vérifiez les DTOs dans les controllers pour les formats exacts des bodies.)

**Postman**
- Importer [postman_collection.json](postman_collection.json).
- Utilisez la requête `Auth - Login` pour récupérer le token et l'enregistrer dans la variable d'environnement `token`.

**Debug & Troubleshooting**
- Erreurs de type `DatatypeConverter` → ajouter les dépendances JAXB (déjà incluses).
- Si Hibernate ne trouve pas la dialecte, vérifiez que `spring.datasource.url`, `spring.datasource.username` et `spring.datasource.password` sont correctement définis (voir `application.properties`).
- Pour forcer la création du schéma, exécutez `create_schema.sql` en tant que superuser puis `seed_data_filled.sql`.

**Notes de développement**
- Branch actuelle utilisée pour les changements: `feature/backend-init`.
- Le JAR généré se trouve dans `target/demo-0.0.1-SNAPSHOT.jar`.

**Prochaine étape suggérée**
- Importer la collection Postman et tester la séquence: register (si besoin) → login → me → créer réservation / récupérer emplois du temps.

Si vous voulez, je peux :
- exécuter automatiquement une requête de test `login` → `me` et vous montrer la réponse, ou
- committer et pousser le README vers la branche `feature/backend-init`.

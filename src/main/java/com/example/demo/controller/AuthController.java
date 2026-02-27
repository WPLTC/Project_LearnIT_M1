package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.dto.UserCreateDTO;
import com.example.demo.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.RegisterStudentDTO;
import com.example.demo.dto.RegisterTeacherDTO;
import com.example.demo.entity.Etudiant;
import com.example.demo.entity.Enseignant;
import com.example.demo.repository.EtudiantRepository;
import com.example.demo.repository.EnseignantRepository;
import com.example.demo.repository.ClasseRepository;
import java.time.LocalDate;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private ClasseRepository classeRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants invalides");
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erreur d'authentification");
        }

        Optional<User> optUser = userRepository.findByEmail(request.getEmail());
        if (optUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur introuvable");
        }
        User user = optUser.get();
        String token = jwtUtil.generateToken(user.getEmail());
        AuthResponse resp = new AuthResponse(token, user.getUsername(), user.getEmail(), user.getRole().name());
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserCreateDTO dto) {
        // check existing email
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email déjà utilisé");
        }
        // check existing username
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nom d'utilisateur déjà utilisé");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .role(User.RoleEnum.valueOf(dto.getRole().name()))
                .build();

        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail());
        AuthResponse resp = new AuthResponse(token, user.getUsername(), user.getEmail(), user.getRole().name());
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PostMapping("/register-student")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody RegisterStudentDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) return ResponseEntity.status(HttpStatus.CONFLICT).body("Email déjà utilisé");
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) return ResponseEntity.status(HttpStatus.CONFLICT).body("Nom d'utilisateur déjà utilisé");

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .role(User.RoleEnum.ETUDIANT)
                .build();
        userRepository.save(user);

        Etudiant etu = new Etudiant();
        etu.setUser(user);
        etu.setNom(dto.getNom());
        etu.setPrenom(dto.getPrenom());
        etu.setEmail(dto.getEmail());
        etu.setMatricule(dto.getMatricule());
        if (dto.getDateNaissance() != null) etu.setDateNaissance(dto.getDateNaissance());
        if (dto.getClasseId() != null) {
            classeRepository.findById(dto.getClasseId()).ifPresent(etu::setClasse);
        }
        etudiantRepository.save(etu);

        String tokenS = jwtUtil.generateToken(user.getEmail());
        AuthResponse respS = new AuthResponse(tokenS, user.getUsername(), user.getEmail(), user.getRole().name());
        return ResponseEntity.status(HttpStatus.CREATED).body(respS);
    }

    @PostMapping("/register-teacher")
    public ResponseEntity<?> registerTeacher(@Valid @RequestBody RegisterTeacherDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) return ResponseEntity.status(HttpStatus.CONFLICT).body("Email déjà utilisé");
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) return ResponseEntity.status(HttpStatus.CONFLICT).body("Nom d'utilisateur déjà utilisé");

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .role(User.RoleEnum.ENSEIGNANT)
                .build();
        userRepository.save(user);

        Enseignant ens = new Enseignant();
        ens.setUser(user);
        ens.setSpecialite(dto.getSpecialite());
        if (dto.getHeuresEnseignees() != null) ens.setHeuresEnseignees(dto.getHeuresEnseignees());
        if (dto.getStatut() != null) ens.setStatut(Enseignant.StatutEnseignant.valueOf(dto.getStatut().name()));
        enseignantRepository.save(ens);

        String tokenT = jwtUtil.generateToken(user.getEmail());
        AuthResponse respT = new AuthResponse(tokenT, user.getUsername(), user.getEmail(), user.getRole().name());
        return ResponseEntity.status(HttpStatus.CREATED).body(respT);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non authentifié");
        }
        String email = authentication.getName();
        Optional<User> opt = userRepository.findByEmail(email);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur introuvable");
        }
        User user = opt.get();
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setRole(UserDTO.RoleEnum.valueOf(user.getRole().name()));
        dto.setCreatedAt(user.getCreatedAt());
        return ResponseEntity.ok(dto);
    }
}

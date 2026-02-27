package com.example.demo.controller;

import com.example.demo.dto.EtudiantDTO;
import com.example.demo.service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
public class EtudiantController {

    @Autowired
    private EtudiantService etudiantService;

    @GetMapping
    public List<EtudiantDTO> getAll() {
        return etudiantService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtudiantDTO> getById(@PathVariable Long id) {
        return etudiantService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@jakarta.validation.Valid @RequestBody EtudiantDTO dto, Authentication authentication) {
        if (!isAdmin(authentication)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(etudiantService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @jakarta.validation.Valid @RequestBody EtudiantDTO dto, Authentication authentication) {
        if (!isAdmin(authentication)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return etudiantService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
        if (!isAdmin(authentication)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        if (etudiantService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<EtudiantDTO> search(@RequestParam String query) {
        return etudiantService.search(query);
    }

    private boolean isAdmin(Authentication authentication) {
        if (authentication == null) return false;
        for (GrantedAuthority a : authentication.getAuthorities()) {
            String auth = a.getAuthority();
            if ("ROLE_ADMIN".equals(auth) || "ADMIN".equals(auth)) return true;
        }
        return false;
    }
}

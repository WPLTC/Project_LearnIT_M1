package com.example.demo.controller;

import com.example.demo.dto.EnseignantDTO;
import com.example.demo.entity.Enseignant;
import com.example.demo.service.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enseignants")
public class EnseignantController {

    @Autowired
    private EnseignantService enseignantService;

    @GetMapping
    public List<EnseignantDTO> getAll() {
        return enseignantService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnseignantDTO> getById(@PathVariable Long id) {
        return enseignantService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@jakarta.validation.Valid @RequestBody EnseignantDTO dto, Authentication authentication) {
        if (!isAdmin(authentication)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(enseignantService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @jakarta.validation.Valid @RequestBody EnseignantDTO dto, Authentication authentication) {
        if (!isAdmin(authentication)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return enseignantService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
        if (!isAdmin(authentication)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        if (enseignantService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<EnseignantDTO> search(@RequestParam String query) {
        return enseignantService.search(query);
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

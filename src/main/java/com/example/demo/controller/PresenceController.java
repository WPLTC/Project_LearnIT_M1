package com.example.demo.controller;

import com.example.demo.dto.PresenceDTO;
import com.example.demo.service.PresenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/presences")
public class PresenceController {

    @Autowired
    private PresenceService presenceService;

    @GetMapping
    public List<PresenceDTO> getAll() {
        return presenceService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PresenceDTO> getById(@PathVariable Long id) {
        return presenceService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PresenceDTO create(@jakarta.validation.Valid @RequestBody PresenceDTO dto) {
        return presenceService.create(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PresenceDTO> update(@PathVariable Long id, @jakarta.validation.Valid @RequestBody PresenceDTO dto) {
        return presenceService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (presenceService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint pour marquer la présence
    @PostMapping("/marquer")
    public PresenceDTO marquerPresence(@jakarta.validation.Valid @RequestBody PresenceDTO dto) {
        return presenceService.marquerPresence(dto);
    }
}

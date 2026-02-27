package com.example.demo.controller;

import com.example.demo.dto.EmploiDuTempsDTO;
import com.example.demo.service.EmploiDuTempsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emplois-du-temps")
public class EmploiDuTempsController {

    @Autowired
    private EmploiDuTempsService emploiDuTempsService;

    @GetMapping
    public List<EmploiDuTempsDTO> getAll() {
        return emploiDuTempsService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmploiDuTempsDTO> getById(@PathVariable Long id) {
        return emploiDuTempsService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmploiDuTempsDTO create(@jakarta.validation.Valid @RequestBody EmploiDuTempsDTO dto) {
        return emploiDuTempsService.create(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmploiDuTempsDTO> update(@PathVariable Long id, @jakarta.validation.Valid @RequestBody EmploiDuTempsDTO dto) {
        return emploiDuTempsService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (emploiDuTempsService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint spécifique pour planifier un cours
    @PostMapping("/planifier")
    public EmploiDuTempsDTO planifier(@jakarta.validation.Valid @RequestBody EmploiDuTempsDTO dto) {
        return emploiDuTempsService.planifier(dto);
    }
}

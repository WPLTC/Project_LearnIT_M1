package com.example.demo.controller;

import com.example.demo.dto.SalleDTO;
import com.example.demo.entity.Salle;
import com.example.demo.repository.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/salles")
public class SalleController {

    @Autowired
    private SalleRepository salleRepository;

    @GetMapping
    public List<SalleDTO> getAll() {
        return salleRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalleDTO> getById(@PathVariable Long id) {
        return salleRepository.findById(id).map(s -> ResponseEntity.ok(toDTO(s))).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public SalleDTO create(@RequestBody SalleDTO dto) {
        Salle s = toEntity(dto);
        return toDTO(salleRepository.save(s));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalleDTO> update(@PathVariable Long id, @RequestBody SalleDTO dto) {
        return salleRepository.findById(id).map(existing -> {
            existing.setNom(dto.getNom());
            existing.setCapacite(dto.getCapacite());
            existing.setEquipements(dto.getEquipements());
            existing.setLocalisation(dto.getLocalisation());
            return ResponseEntity.ok(toDTO(salleRepository.save(existing)));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (salleRepository.existsById(id)) {
            salleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private SalleDTO toDTO(Salle s) {
        SalleDTO dto = new SalleDTO();
        dto.setId(s.getId());
        dto.setNom(s.getNom());
        dto.setCapacite(s.getCapacite());
        dto.setEquipements(s.getEquipements());
        dto.setLocalisation(s.getLocalisation());
        return dto;
    }

    private Salle toEntity(SalleDTO dto) {
        Salle s = new Salle();
        s.setId(dto.getId());
        s.setNom(dto.getNom());
        s.setCapacite(dto.getCapacite());
        s.setEquipements(dto.getEquipements());
        s.setLocalisation(dto.getLocalisation());
        return s;
    }
}

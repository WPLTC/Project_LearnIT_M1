package com.example.demo.controller;

import com.example.demo.dto.ClasseDTO;
import com.example.demo.service.ClasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClasseController {

    @Autowired
    private ClasseService classeService;

    @GetMapping
    public List<ClasseDTO> getAll() {
        return classeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClasseDTO> getById(@PathVariable Long id) {
        return classeService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ClasseDTO create(@jakarta.validation.Valid @RequestBody ClasseDTO dto) {
        return classeService.create(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClasseDTO> update(@PathVariable Long id, @jakarta.validation.Valid @RequestBody ClasseDTO dto) {
        return classeService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (classeService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

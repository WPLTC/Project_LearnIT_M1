package com.example.demo.controller;

import com.example.demo.dto.EvaluationDTO;
import com.example.demo.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping
    public List<EvaluationDTO> getAll() {
        return evaluationService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluationDTO> getById(@PathVariable Long id) {
        return evaluationService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EvaluationDTO create(@jakarta.validation.Valid @RequestBody EvaluationDTO dto) {
        return evaluationService.create(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvaluationDTO> update(@PathVariable Long id, @jakarta.validation.Valid @RequestBody EvaluationDTO dto) {
        return evaluationService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (evaluationService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

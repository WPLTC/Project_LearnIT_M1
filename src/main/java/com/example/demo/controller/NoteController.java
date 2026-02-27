package com.example.demo.controller;

import com.example.demo.dto.NoteDTO;
import com.example.demo.entity.Etudiant;
import com.example.demo.entity.Enseignant;
import com.example.demo.entity.Evaluation;
import com.example.demo.entity.Note;
import com.example.demo.repository.EtudiantRepository;
import com.example.demo.repository.EnseignantRepository;
import com.example.demo.repository.EvaluationRepository;
import com.example.demo.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private EnseignantRepository enseignantRepository;

    @GetMapping
    public List<NoteDTO> getAll() {
        return noteRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> getById(@PathVariable Long id) {
        return noteRepository.findById(id).map(n -> ResponseEntity.ok(toDTO(n))).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody NoteDTO dto) {
        // validate referenced entities
        Etudiant etu = etudiantRepository.findById(dto.getEtudiantId()).orElse(null);
        Evaluation eval = evaluationRepository.findById(dto.getEvaluationId()).orElse(null);
        Enseignant ens = enseignantRepository.findById(dto.getSaisieParId()).orElse(null);
        if (etu == null || eval == null || ens == null) {
            return ResponseEntity.badRequest().body("Références invalides pour etudiant/evaluation/enseignant");
        }
        Note n = new Note();
        n.setEtudiant(etu);
        n.setEvaluation(eval);
        n.setValeur(dto.getValeur());
        n.setCommentaire(dto.getCommentaire());
        n.setSaisiePar(ens);
        return ResponseEntity.ok(toDTO(noteRepository.save(n)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody NoteDTO dto) {
        return noteRepository.findById(id).map(existing -> {
            if (dto.getEtudiantId() != null) etudiantRepository.findById(dto.getEtudiantId()).ifPresent(existing::setEtudiant);
            if (dto.getEvaluationId() != null) evaluationRepository.findById(dto.getEvaluationId()).ifPresent(existing::setEvaluation);
            if (dto.getSaisieParId() != null) enseignantRepository.findById(dto.getSaisieParId()).ifPresent(existing::setSaisiePar);
            if (dto.getValeur() != null) existing.setValeur(dto.getValeur());
            existing.setCommentaire(dto.getCommentaire());
            return ResponseEntity.ok(toDTO(noteRepository.save(existing)));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (noteRepository.existsById(id)) {
            noteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private NoteDTO toDTO(Note n) {
        NoteDTO dto = new NoteDTO();
        dto.setId(n.getId());
        if (n.getEtudiant() != null) dto.setEtudiantId(n.getEtudiant().getId());
        if (n.getEvaluation() != null) dto.setEvaluationId(n.getEvaluation().getId());
        dto.setValeur(n.getValeur());
        dto.setCommentaire(n.getCommentaire());
        if (n.getSaisiePar() != null) dto.setSaisieParId(n.getSaisiePar().getId());
        return dto;
    }
}

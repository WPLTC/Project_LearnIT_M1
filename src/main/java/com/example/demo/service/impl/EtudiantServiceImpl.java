package com.example.demo.service.impl;

import com.example.demo.dto.EtudiantDTO;
import com.example.demo.entity.Etudiant;
import com.example.demo.repository.EtudiantRepository;
import com.example.demo.service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EtudiantServiceImpl implements EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Override
    public List<EtudiantDTO> getAll() {
        return etudiantRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EtudiantDTO> getById(Long id) {
        return etudiantRepository.findById(id).map(this::toDTO);
    }

    @Override
    public EtudiantDTO create(EtudiantDTO dto) {
        Etudiant etudiant = toEntity(dto);
        return toDTO(etudiantRepository.save(etudiant));
    }

    @Override
    public Optional<EtudiantDTO> update(Long id, EtudiantDTO dto) {
        return etudiantRepository.findById(id).map(existing -> {
            existing.setNom(dto.getNom());
            existing.setPrenom(dto.getPrenom());
            existing.setEmail(dto.getEmail());
            existing.setMatricule(dto.getMatricule());
            existing.setDateNaissance(dto.getDateNaissance());
            // Classe et user non modifiés ici (gérés via d'autres services)
            return toDTO(etudiantRepository.save(existing));
        });
    }

    @Override
    public boolean delete(Long id) {
        if (etudiantRepository.existsById(id)) {
            etudiantRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<EtudiantDTO> search(String query) {
        return etudiantRepository.findByNomContainingIgnoreCase(query).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Méthodes de mapping (à adapter selon la structure réelle des DTO/Entity)

    private EtudiantDTO toDTO(Etudiant etudiant) {
        EtudiantDTO dto = new EtudiantDTO();
        dto.setId(etudiant.getId());
        dto.setNom(etudiant.getNom());
        dto.setPrenom(etudiant.getPrenom());
        dto.setEmail(etudiant.getEmail());
        dto.setMatricule(etudiant.getMatricule());
        dto.setDateNaissance(etudiant.getDateNaissance());
        if (etudiant.getClasse() != null) {
            dto.setClasseId(etudiant.getClasse().getId());
        }
        if (etudiant.getUser() != null) {
            dto.setUserId(etudiant.getUser().getId());
        }
        return dto;
    }


    private Etudiant toEntity(EtudiantDTO dto) {
        Etudiant etudiant = new Etudiant();
        etudiant.setId(dto.getId());
        etudiant.setNom(dto.getNom());
        etudiant.setPrenom(dto.getPrenom());
        etudiant.setEmail(dto.getEmail());
        etudiant.setMatricule(dto.getMatricule());
        etudiant.setDateNaissance(dto.getDateNaissance());
        // Classe et user à lier via service si besoin
        return etudiant;
    }
}

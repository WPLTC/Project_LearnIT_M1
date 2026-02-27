package com.example.demo.service.impl;

import com.example.demo.dto.ModuleDTO;
import com.example.demo.entity.Module;
import com.example.demo.repository.ModuleRepository;
import com.example.demo.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    @Override
    public List<ModuleDTO> getAll() {
        return moduleRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ModuleDTO> getById(Long id) {
        return moduleRepository.findById(id).map(this::toDTO);
    }

    @Override
    public ModuleDTO create(ModuleDTO dto) {
        Module module = toEntity(dto);
        return toDTO(moduleRepository.save(module));
    }

    @Override
    public Optional<ModuleDTO> update(Long id, ModuleDTO dto) {
        return moduleRepository.findById(id).map(existing -> {
            existing.setNom(dto.getNom());
            existing.setDescription(dto.getDescription());
            existing.setCredits(dto.getCredits());
            // Classe et enseignant non modifiés ici (gérés via d'autres services)
            return toDTO(moduleRepository.save(existing));
        });
    }

    @Override
    public boolean delete(Long id) {
        if (moduleRepository.existsById(id)) {
            moduleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Méthodes de mapping (à adapter selon la structure réelle des DTO/Entity)

    private ModuleDTO toDTO(Module module) {
        ModuleDTO dto = new ModuleDTO();
        dto.setId(module.getId());
        dto.setNom(module.getNom());
        dto.setDescription(module.getDescription());
        dto.setCredits(module.getCredits());
        if (module.getClasse() != null) {
            dto.setClasseId(module.getClasse().getId());
        }
        if (module.getEnseignant() != null) {
            dto.setEnseignantId(module.getEnseignant().getId());
        }
        return dto;
    }


    private Module toEntity(ModuleDTO dto) {
        Module module = new Module();
        module.setId(dto.getId());
        module.setNom(dto.getNom());
        module.setDescription(dto.getDescription());
        module.setCredits(dto.getCredits());
        // Classe et enseignant à lier via service si besoin
        return module;
    }
}

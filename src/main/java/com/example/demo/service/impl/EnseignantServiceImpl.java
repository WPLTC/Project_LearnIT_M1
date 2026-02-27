package com.example.demo.service.impl;

import com.example.demo.dto.EnseignantDTO;
import com.example.demo.entity.Enseignant;
import com.example.demo.repository.EnseignantRepository;
import com.example.demo.service.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnseignantServiceImpl implements EnseignantService {

    @Autowired
    private EnseignantRepository enseignantRepository;

    @Override
    public List<EnseignantDTO> getAll() {
        return enseignantRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EnseignantDTO> getById(Long id) {
        return enseignantRepository.findById(id).map(this::toDTO);
    }

    @Override
    public EnseignantDTO create(EnseignantDTO dto) {
        Enseignant enseignant = toEntity(dto);
        return toDTO(enseignantRepository.save(enseignant));
    }

    @Override
    public Optional<EnseignantDTO> update(Long id, EnseignantDTO dto) {
        return enseignantRepository.findById(id).map(existing -> {
            existing.setSpecialite(dto.getSpecialite());
            existing.setHeuresEnseignees(dto.getHeuresEnseignees());
            // Conversion enum sécurisée
            if (dto.getStatut() != null) {
                existing.setStatut(
                    Enseignant.StatutEnseignant.valueOf(dto.getStatut().name())
                );
            }
            // UserId non modifiable ici (lié à la création)
            return toDTO(enseignantRepository.save(existing));
        });
    }

    @Override
    public boolean delete(Long id) {
        if (enseignantRepository.existsById(id)) {
            enseignantRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<EnseignantDTO> search(String query) {
        return enseignantRepository.findBySpecialiteContainingIgnoreCase(query).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Méthodes de mapping (à adapter selon la structure réelle des DTO/Entity)
    private EnseignantDTO toDTO(Enseignant enseignant) {
        EnseignantDTO dto = new EnseignantDTO();
        dto.setId(enseignant.getId());
        dto.setSpecialite(enseignant.getSpecialite());
        dto.setHeuresEnseignees(enseignant.getHeuresEnseignees());
        // Conversion enum
        if (enseignant.getStatut() != null) {
            dto.setStatut(EnseignantDTO.StatutEnseignant.valueOf(enseignant.getStatut().name()));
        }
        // User mapping (id et objet)
        if (enseignant.getUser() != null) {
            dto.setUserId(enseignant.getUser().getId());
            // DTO user minimal (à enrichir selon UserDTO)
            // dto.setUser(userMapper.toDTO(enseignant.getUser())); // si UserDTO existe
        }
        return dto;
    }

    // Conversion enum
    private Enseignant toEntity(EnseignantDTO dto) {
        Enseignant enseignant = new Enseignant();
        enseignant.setId(dto.getId());
        enseignant.setSpecialite(dto.getSpecialite());
        enseignant.setHeuresEnseignees(dto.getHeuresEnseignees());
        // Conversion enum
        if (dto.getStatut() != null) {
            enseignant.setStatut(Enseignant.StatutEnseignant.valueOf(dto.getStatut().name()));
        }
        // User non géré ici (lié à la création ou via UserService)
        return enseignant;
    }
}

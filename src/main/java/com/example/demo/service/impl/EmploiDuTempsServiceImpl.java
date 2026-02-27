package com.example.demo.service.impl;

import com.example.demo.dto.EmploiDuTempsDTO;
import com.example.demo.dto.ModuleDTO;
import com.example.demo.dto.SalleDTO;
import com.example.demo.dto.EnseignantDTO;
import com.example.demo.entity.EmploiDuTemps;
import com.example.demo.entity.Module;
import com.example.demo.entity.Salle;
import com.example.demo.entity.Enseignant;
import com.example.demo.repository.EmploiDuTempsRepository;
import com.example.demo.service.EmploiDuTempsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmploiDuTempsServiceImpl implements EmploiDuTempsService {

    @Autowired
    private EmploiDuTempsRepository emploiDuTempsRepository;

    @Override
    public List<EmploiDuTempsDTO> getAll() {
        return emploiDuTempsRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EmploiDuTempsDTO> getById(Long id) {
        return emploiDuTempsRepository.findById(id).map(this::toDTO);
    }

    @Override
    public EmploiDuTempsDTO create(EmploiDuTempsDTO dto) {
        EmploiDuTemps emploi = toEntity(dto);
        return toDTO(emploiDuTempsRepository.save(emploi));
    }

    @Override
    public Optional<EmploiDuTempsDTO> update(Long id, EmploiDuTempsDTO dto) {
        return emploiDuTempsRepository.findById(id).map(existing -> {
            existing.setDateDebut(dto.getDateDebut());
            existing.setDateFin(dto.getDateFin());
            existing.setRecurrence(dto.getRecurrence());
            if (dto.getStatut() != null) {
                existing.setStatut(com.example.demo.entity.EmploiDuTemps.StatutEmploiDuTemps.valueOf(dto.getStatut().name()));
            }
            // Module, enseignant, salle non modifiés ici (gérés via d'autres services)
            return toDTO(emploiDuTempsRepository.save(existing));
        });
    }

    @Override
    public boolean delete(Long id) {
        if (emploiDuTempsRepository.existsById(id)) {
            emploiDuTempsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public EmploiDuTempsDTO planifier(EmploiDuTempsDTO dto) {
        validateDates(dto.getDateDebut(), dto.getDateFin());

        EmploiDuTemps emploi = toEntity(dto);

        // use repository queries to check overlaps for salle and enseignant
        if (emploi.getSalle() != null && emploi.getSalle().getId() != null) {
            boolean salleConflict;
            if (emploi.getId() == null) {
                salleConflict = !emploiDuTempsRepository.findOverlappingForSalle(emploi.getSalle().getId(), emploi.getDateDebut(), emploi.getDateFin()).isEmpty();
            } else {
                salleConflict = !emploiDuTempsRepository.findOverlappingForSalleExcluding(emploi.getSalle().getId(), emploi.getDateDebut(), emploi.getDateFin(), emploi.getId()).isEmpty();
            }
            if (salleConflict) throw new IllegalArgumentException("Conflit de planification détecté pour la salle");
        }

        if (emploi.getEnseignant() != null && emploi.getEnseignant().getId() != null) {
            boolean enseignantConflict;
            if (emploi.getId() == null) {
                enseignantConflict = !emploiDuTempsRepository.findOverlappingForEnseignant(emploi.getEnseignant().getId(), emploi.getDateDebut(), emploi.getDateFin()).isEmpty();
            } else {
                enseignantConflict = !emploiDuTempsRepository.findOverlappingForEnseignantExcluding(emploi.getEnseignant().getId(), emploi.getDateDebut(), emploi.getDateFin(), emploi.getId()).isEmpty();
            }
            if (enseignantConflict) throw new IllegalArgumentException("Conflit de planification détecté pour l'enseignant");
        }

        return toDTO(emploiDuTempsRepository.save(emploi));
    }

    // Méthodes de mapping (à adapter selon la structure réelle des DTO/Entity)

    private EmploiDuTempsDTO toDTO(EmploiDuTemps emploi) {
        EmploiDuTempsDTO dto = new EmploiDuTempsDTO();
        dto.setId(emploi.getId());
        dto.setDateDebut(emploi.getDateDebut());
        dto.setDateFin(emploi.getDateFin());
        dto.setRecurrence(emploi.getRecurrence());
        if (emploi.getStatut() != null) {
            dto.setStatut(EmploiDuTempsDTO.StatutEmploiDuTemps.valueOf(emploi.getStatut().name()));
        }
        if (emploi.getModule() != null) {
            dto.setModuleId(emploi.getModule().getId());
            ModuleDTO m = new ModuleDTO();
            m.setId(emploi.getModule().getId());
            m.setNom(emploi.getModule().getNom());
            dto.setModule(m);
        }
        if (emploi.getEnseignant() != null) {
            dto.setEnseignantId(emploi.getEnseignant().getId());
            EnseignantDTO en = new EnseignantDTO();
            en.setId(emploi.getEnseignant().getId());
            en.setSpecialite(emploi.getEnseignant().getSpecialite());
            dto.setEnseignant(en);
        }
        if (emploi.getSalle() != null) {
            dto.setSalleId(emploi.getSalle().getId());
            SalleDTO s = new SalleDTO();
            s.setId(emploi.getSalle().getId());
            s.setNom(emploi.getSalle().getNom());
            dto.setSalle(s);
        }
        return dto;
    }


    private EmploiDuTemps toEntity(EmploiDuTempsDTO dto) {
        EmploiDuTemps emploi = new EmploiDuTemps();
        emploi.setId(dto.getId());
        emploi.setDateDebut(dto.getDateDebut());
        emploi.setDateFin(dto.getDateFin());
        emploi.setRecurrence(dto.getRecurrence());
        if (dto.getStatut() != null) {
            emploi.setStatut(com.example.demo.entity.EmploiDuTemps.StatutEmploiDuTemps.valueOf(dto.getStatut().name()));
        }
        if (dto.getModuleId() != null) {
            Module m = new Module();
            m.setId(dto.getModuleId());
            emploi.setModule(m);
        }
        if (dto.getEnseignantId() != null) {
            Enseignant en = new Enseignant();
            en.setId(dto.getEnseignantId());
            emploi.setEnseignant(en);
        }
        if (dto.getSalleId() != null) {
            Salle s = new Salle();
            s.setId(dto.getSalleId());
            emploi.setSalle(s);
        }
        return emploi;
    }

    private boolean overlaps(LocalDateTime aStart, LocalDateTime aEnd, LocalDateTime bStart, LocalDateTime bEnd) {
        if (aStart == null || aEnd == null || bStart == null || bEnd == null) return false;
        return aStart.isBefore(bEnd) && bStart.isBefore(aEnd);
    }

    private void validateDates(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) throw new IllegalArgumentException("Dates de début et fin obligatoires");
        if (!start.isBefore(end)) throw new IllegalArgumentException("La date de début doit être avant la date de fin");
    }
}

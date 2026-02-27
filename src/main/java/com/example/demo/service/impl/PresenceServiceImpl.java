package com.example.demo.service.impl;

import com.example.demo.dto.PresenceDTO;
import com.example.demo.entity.Presence;
import com.example.demo.repository.PresenceRepository;
import com.example.demo.service.PresenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PresenceServiceImpl implements PresenceService {

    @Autowired
    private PresenceRepository presenceRepository;

    @Override
    public List<PresenceDTO> getAll() {
        return presenceRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PresenceDTO> getById(Long id) {
        return presenceRepository.findById(id).map(this::toDTO);
    }

    @Override
    public PresenceDTO create(PresenceDTO dto) {
        Presence presence = toEntity(dto);
        // Ensure default present value
        if (presence.getPresent() == null) {
            presence.setPresent(Boolean.TRUE);
        }
        return toDTO(presenceRepository.save(presence));
    }

    @Override
    public Optional<PresenceDTO> update(Long id, PresenceDTO dto) {
        return presenceRepository.findById(id).map(existing -> {
            existing.setPresent(dto.getPresent());
            existing.setJustification(dto.getJustification());
            // marquePar and other relations handled externally
            return toDTO(presenceRepository.save(existing));
        });
    }

    @Override
    public boolean delete(Long id) {
        if (presenceRepository.existsById(id)) {
            presenceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public PresenceDTO marquerPresence(PresenceDTO dto) {
        Presence presence = toEntity(dto);
        presence.setPresent(true);
        return toDTO(presenceRepository.save(presence));
    }

    private PresenceDTO toDTO(Presence presence) {
        PresenceDTO dto = new PresenceDTO();
        dto.setId(presence.getId());
        if (presence.getEmploiDuTemps() != null) {
            dto.setEmploiDuTempsId(presence.getEmploiDuTemps().getId());
        }
        if (presence.getUser() != null) {
            dto.setUserId(presence.getUser().getId());
        }
        dto.setPresent(presence.getPresent());
        dto.setJustification(presence.getJustification());
        if (presence.getMarquePar() != null) {
            dto.setMarqueParId(presence.getMarquePar().getId());
        }
        return dto;
    }

    private Presence toEntity(PresenceDTO dto) {
        Presence presence = new Presence();
        presence.setId(dto.getId());
        presence.setPresent(dto.getPresent() != null ? dto.getPresent() : Boolean.TRUE);
        presence.setJustification(dto.getJustification());
        // EmploiDuTemps, User and marquePar should be linked via their services if needed
        return presence;
    }
}

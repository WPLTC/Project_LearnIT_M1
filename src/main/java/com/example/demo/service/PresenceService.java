package com.example.demo.service;

import com.example.demo.dto.PresenceDTO;
import java.util.List;
import java.util.Optional;

public interface PresenceService {
    List<PresenceDTO> getAll();
    Optional<PresenceDTO> getById(Long id);
    PresenceDTO create(PresenceDTO dto);
    Optional<PresenceDTO> update(Long id, PresenceDTO dto);
    boolean delete(Long id);

    // Marquer la présence d'un utilisateur
    PresenceDTO marquerPresence(PresenceDTO dto);
}

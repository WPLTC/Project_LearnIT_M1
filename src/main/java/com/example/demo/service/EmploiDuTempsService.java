package com.example.demo.service;

import com.example.demo.dto.EmploiDuTempsDTO;
import java.util.List;
import java.util.Optional;

public interface EmploiDuTempsService {
    List<EmploiDuTempsDTO> getAll();
    Optional<EmploiDuTempsDTO> getById(Long id);
    EmploiDuTempsDTO create(EmploiDuTempsDTO dto);
    Optional<EmploiDuTempsDTO> update(Long id, EmploiDuTempsDTO dto);
    boolean delete(Long id);
    EmploiDuTempsDTO planifier(EmploiDuTempsDTO dto);
}

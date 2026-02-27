package com.example.demo.service;

import com.example.demo.dto.EnseignantDTO;
import java.util.List;
import java.util.Optional;

public interface EnseignantService {
    List<EnseignantDTO> getAll();
    Optional<EnseignantDTO> getById(Long id);
    EnseignantDTO create(EnseignantDTO dto);
    Optional<EnseignantDTO> update(Long id, EnseignantDTO dto);
    boolean delete(Long id);
    List<EnseignantDTO> search(String query);
}

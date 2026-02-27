package com.example.demo.service;

import com.example.demo.dto.EtudiantDTO;
import java.util.List;
import java.util.Optional;

public interface EtudiantService {
    List<EtudiantDTO> getAll();
    Optional<EtudiantDTO> getById(Long id);
    EtudiantDTO create(EtudiantDTO dto);
    Optional<EtudiantDTO> update(Long id, EtudiantDTO dto);
    boolean delete(Long id);
    List<EtudiantDTO> search(String query);
}

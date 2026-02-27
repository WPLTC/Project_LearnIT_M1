package com.example.demo.service;

import com.example.demo.dto.ModuleDTO;
import java.util.List;
import java.util.Optional;

public interface ModuleService {
    List<ModuleDTO> getAll();
    Optional<ModuleDTO> getById(Long id);
    ModuleDTO create(ModuleDTO dto);
    Optional<ModuleDTO> update(Long id, ModuleDTO dto);
    boolean delete(Long id);
}

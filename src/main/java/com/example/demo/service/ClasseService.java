package com.example.demo.service;

import com.example.demo.dto.ClasseDTO;
import java.util.List;
import java.util.Optional;

public interface ClasseService {
    List<ClasseDTO> getAll();
    Optional<ClasseDTO> getById(Long id);
    ClasseDTO create(ClasseDTO dto);
    Optional<ClasseDTO> update(Long id, ClasseDTO dto);
    boolean delete(Long id);
}

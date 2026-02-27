package com.example.demo.service;

import com.example.demo.dto.EvaluationDTO;
import java.util.List;
import java.util.Optional;

public interface EvaluationService {
    List<EvaluationDTO> getAll();
    Optional<EvaluationDTO> getById(Long id);
    EvaluationDTO create(EvaluationDTO dto);
    Optional<EvaluationDTO> update(Long id, EvaluationDTO dto);
    boolean delete(Long id);
}

package com.example.demo.service.impl;

import com.example.demo.dto.EvaluationDTO;
import com.example.demo.entity.Evaluation;
import com.example.demo.repository.EvaluationRepository;
import com.example.demo.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Override
    public List<EvaluationDTO> getAll() {
        return evaluationRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EvaluationDTO> getById(Long id) {
        return evaluationRepository.findById(id).map(this::toDTO);
    }

    @Override
    public EvaluationDTO create(EvaluationDTO dto) {
        Evaluation evaluation = toEntity(dto);
        return toDTO(evaluationRepository.save(evaluation));
    }

    @Override
    public Optional<EvaluationDTO> update(Long id, EvaluationDTO dto) {
        return evaluationRepository.findById(id).map(existing -> {
            existing.setNom(dto.getNom());
            if (dto.getType() != null) {
                existing.setType(Evaluation.TypeEvaluation.valueOf(dto.getType().name()));
            }
            existing.setCoefficient(dto.getCoefficient());
            existing.setDate(dto.getDate());
            // Module association handled elsewhere if needed
            return toDTO(evaluationRepository.save(existing));
        });
    }

    @Override
    public boolean delete(Long id) {
        if (evaluationRepository.existsById(id)) {
            evaluationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Méthodes de mapping (à adapter selon la structure réelle des DTO/Entity)
    private EvaluationDTO toDTO(Evaluation evaluation) {
        EvaluationDTO dto = new EvaluationDTO();
        dto.setId(evaluation.getId());
        dto.setNom(evaluation.getNom());
        if (evaluation.getModule() != null) {
            dto.setModuleId(evaluation.getModule().getId());
        }
        if (evaluation.getType() != null) {
            dto.setType(EvaluationDTO.TypeEvaluation.valueOf(evaluation.getType().name()));
        }
        dto.setCoefficient(evaluation.getCoefficient());
        dto.setDate(evaluation.getDate());
        return dto;
    }

    private Evaluation toEntity(EvaluationDTO dto) {
        Evaluation evaluation = new Evaluation();
        evaluation.setId(dto.getId());
        evaluation.setNom(dto.getNom());
        if (dto.getType() != null) {
            evaluation.setType(Evaluation.TypeEvaluation.valueOf(dto.getType().name()));
        }
        evaluation.setCoefficient(dto.getCoefficient());
        evaluation.setDate(dto.getDate());
        // Module association to be linked via ModuleService if needed
        return evaluation;
    }
}

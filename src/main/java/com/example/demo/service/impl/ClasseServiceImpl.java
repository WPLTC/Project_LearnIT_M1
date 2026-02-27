package com.example.demo.service.impl;

import com.example.demo.dto.ClasseDTO;
import com.example.demo.entity.Classe;
import com.example.demo.repository.ClasseRepository;
import com.example.demo.service.ClasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClasseServiceImpl implements ClasseService {

    @Autowired
    private ClasseRepository classeRepository;

    @Override
    public List<ClasseDTO> getAll() {
        return classeRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClasseDTO> getById(Long id) {
        return classeRepository.findById(id).map(this::toDTO);
    }

    @Override
    public ClasseDTO create(ClasseDTO dto) {
        Classe classe = toEntity(dto);
        return toDTO(classeRepository.save(classe));
    }

    @Override
    public Optional<ClasseDTO> update(Long id, ClasseDTO dto) {
        return classeRepository.findById(id).map(existing -> {
            existing.setNom(dto.getNom());
            existing.setNiveau(dto.getNiveau());
            existing.setCapacite(dto.getCapacite());
            existing.setFiliere(dto.getFiliere());
            return toDTO(classeRepository.save(existing));
        });
    }

    @Override
    public boolean delete(Long id) {
        if (classeRepository.existsById(id)) {
            classeRepository.deleteById(id);
            return true;
        }
        return false;
    }


    private ClasseDTO toDTO(Classe classe) {
        ClasseDTO dto = new ClasseDTO();
        dto.setId(classe.getId());
        dto.setNom(classe.getNom());
        dto.setNiveau(classe.getNiveau());
        dto.setCapacite(classe.getCapacite());
        dto.setFiliere(classe.getFiliere());
        // Si besoin, on peut ajouter le mapping des étudiants et modules ici
        // Exemple :
        // dto.setEtudiants(classe.getEtudiants().stream().map(etudiantMapper::toDTO).collect(Collectors.toList()));
        // dto.setModules(classe.getModules().stream().map(moduleMapper::toDTO).collect(Collectors.toList()));
        return dto;
    }


    private Classe toEntity(ClasseDTO dto) {
        Classe classe = new Classe();
        classe.setId(dto.getId());
        classe.setNom(dto.getNom());
        classe.setNiveau(dto.getNiveau());
        classe.setCapacite(dto.getCapacite());
        classe.setFiliere(dto.getFiliere());
        // Les listes d'étudiants et modules sont gérées via d'autres services ou lors de la création
        return classe;
    }
}

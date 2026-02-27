package com.example.demo.service;

import com.example.demo.dto.ReservationDTO;
import java.util.List;
import java.util.Optional;

public interface ReservationService {
    List<ReservationDTO> getAll();
    Optional<ReservationDTO> getById(Long id);
    ReservationDTO create(ReservationDTO dto);
    Optional<ReservationDTO> update(Long id, ReservationDTO dto);
    boolean delete(Long id);
    List<ReservationDTO> getDisponibilites(Long salleId);
}

package com.example.demo.service.impl;

import com.example.demo.dto.ReservationDTO;
import com.example.demo.dto.SalleDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.Salle;
import com.example.demo.entity.User;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public List<ReservationDTO> getAll() {
        return reservationRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ReservationDTO> getById(Long id) {
        return reservationRepository.findById(id).map(this::toDTO);
    }

    @Override
    public ReservationDTO create(ReservationDTO dto) {
        validateDates(dto.getDateDebut(), dto.getDateFin());
        // check overlaps for the salle using repository query
        if (dto.getSalleId() != null) {
            boolean overlap = !reservationRepository.findOverlappingForSalle(dto.getSalleId(), dto.getDateDebut(), dto.getDateFin()).isEmpty();
            if (overlap) {
                throw new IllegalArgumentException("La salle est déjà réservée pour cet intervalle");
            }
        }
        Reservation reservation = toEntity(dto);
        return toDTO(reservationRepository.save(reservation));
    }

    @Override
    public Optional<ReservationDTO> update(Long id, ReservationDTO dto) {
        return reservationRepository.findById(id).map(existing -> {
            if (dto.getDateDebut() != null && dto.getDateFin() != null) {
                validateDates(dto.getDateDebut(), dto.getDateFin());
            }
            // apply fields if provided
            if (dto.getDateDebut() != null) existing.setDateDebut(dto.getDateDebut());
            if (dto.getDateFin() != null) existing.setDateFin(dto.getDateFin());
            if (dto.getMotif() != null) existing.setMotif(dto.getMotif());
            if (dto.getStatut() != null) {
                existing.setStatut(Reservation.StatutReservation.valueOf(dto.getStatut().name()));
            }
            if (dto.getSalleId() != null) {
                Salle s = new Salle();
                s.setId(dto.getSalleId());
                existing.setSalle(s);
            }
            if (dto.getUserId() != null) {
                User u = new User();
                u.setId(dto.getUserId());
                existing.setUser(u);
            }

            // check for overlaps on the salle using repository query excluding current reservation
            Long salleId = existing.getSalle() != null ? existing.getSalle().getId() : null;
            if (salleId != null) {
                LocalDateTime start = existing.getDateDebut();
                LocalDateTime end = existing.getDateFin();
                boolean overlap = !reservationRepository.findOverlappingForSalleExcluding(salleId, start, end, existing.getId()).isEmpty();
                if (overlap) {
                    throw new IllegalArgumentException("Conflit de réservation détecté pour la salle");
                }
            }

            return toDTO(reservationRepository.save(existing));
        });
    }

    @Override
    public boolean delete(Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<ReservationDTO> getDisponibilites(Long salleId) {
        // Logique à compléter selon la structure réelle
        return reservationRepository.findBySalleId(salleId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Méthodes de mapping (à adapter selon la structure réelle des DTO/Entity)
    private ReservationDTO toDTO(Reservation reservation) {
        ReservationDTO dto = new ReservationDTO();
        dto.setId(reservation.getId());
        if (reservation.getUser() != null) {
            dto.setUserId(reservation.getUser().getId());
            UserDTO u = new UserDTO();
            u.setId(reservation.getUser().getId());
            u.setUsername(reservation.getUser().getUsername());
            dto.setUser(u);
        }
        if (reservation.getSalle() != null) {
            dto.setSalleId(reservation.getSalle().getId());
            SalleDTO s = new SalleDTO();
            s.setId(reservation.getSalle().getId());
            s.setNom(reservation.getSalle().getNom());
            dto.setSalle(s);
        }
        dto.setDateDebut(reservation.getDateDebut());
        dto.setDateFin(reservation.getDateFin());
        dto.setMotif(reservation.getMotif());
        if (reservation.getStatut() != null) {
            dto.setStatut(ReservationDTO.StatutReservation.valueOf(reservation.getStatut().name()));
        }
        if (reservation.getValidatedBy() != null) {
            dto.setValidatedBy(reservation.getValidatedBy().getId());
            UserDTO v = new UserDTO();
            v.setId(reservation.getValidatedBy().getId());
            v.setUsername(reservation.getValidatedBy().getUsername());
            dto.setValidateur(v);
        }
        return dto;
    }

    private Reservation toEntity(ReservationDTO dto) {
        Reservation reservation = new Reservation();
        reservation.setId(dto.getId());
        if (dto.getUserId() != null) {
            User u = new User();
            u.setId(dto.getUserId());
            reservation.setUser(u);
        }
        if (dto.getSalleId() != null) {
            Salle s = new Salle();
            s.setId(dto.getSalleId());
            reservation.setSalle(s);
        }
        reservation.setDateDebut(dto.getDateDebut());
        reservation.setDateFin(dto.getDateFin());
        reservation.setMotif(dto.getMotif());
        if (dto.getStatut() != null) {
            reservation.setStatut(Reservation.StatutReservation.valueOf(dto.getStatut().name()));
        }
        if (dto.getValidatedBy() != null) {
            User v = new User();
            v.setId(dto.getValidatedBy());
            reservation.setValidatedBy(v);
        }
        return reservation;
    }

    private boolean overlaps(LocalDateTime aStart, LocalDateTime aEnd, LocalDateTime bStart, LocalDateTime bEnd) {
        if (aStart == null || aEnd == null || bStart == null || bEnd == null) return false;
        return aStart.isBefore(bEnd) && bStart.isBefore(aEnd);
    }

    private void validateDates(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) throw new IllegalArgumentException("Dates de début et fin obligatoires");
        if (!start.isBefore(end)) throw new IllegalArgumentException("La date de début doit être avant la date de fin");
    }
}

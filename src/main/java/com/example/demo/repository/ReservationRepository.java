package com.example.demo.repository;

import com.example.demo.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	List<Reservation> findBySalleId(Long salleId);

	@Query("select r from Reservation r where r.salle.id = :salleId and r.dateDebut < :end and r.dateFin > :start")
	List<Reservation> findOverlappingForSalle(@Param("salleId") Long salleId,
											 @Param("start") LocalDateTime start,
											 @Param("end") LocalDateTime end);

	@Query("select r from Reservation r where r.salle.id = :salleId and r.id <> :excludeId and r.dateDebut < :end and r.dateFin > :start")
	List<Reservation> findOverlappingForSalleExcluding(@Param("salleId") Long salleId,
													 @Param("start") LocalDateTime start,
													 @Param("end") LocalDateTime end,
													 @Param("excludeId") Long excludeId);
}

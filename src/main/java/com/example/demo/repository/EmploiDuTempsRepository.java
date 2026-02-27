package com.example.demo.repository;

import com.example.demo.entity.EmploiDuTemps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmploiDuTempsRepository extends JpaRepository<EmploiDuTemps, Long> {

	@Query("select e from EmploiDuTemps e where e.salle.id = :salleId and e.dateDebut < :end and e.dateFin > :start")
	List<EmploiDuTemps> findOverlappingForSalle(@Param("salleId") Long salleId,
											   @Param("start") LocalDateTime start,
											   @Param("end") LocalDateTime end);

	@Query("select e from EmploiDuTemps e where e.enseignant.id = :enseignantId and e.dateDebut < :end and e.dateFin > :start")
	List<EmploiDuTemps> findOverlappingForEnseignant(@Param("enseignantId") Long enseignantId,
													@Param("start") LocalDateTime start,
													@Param("end") LocalDateTime end);

	@Query("select e from EmploiDuTemps e where e.salle.id = :salleId and e.id <> :excludeId and e.dateDebut < :end and e.dateFin > :start")
	List<EmploiDuTemps> findOverlappingForSalleExcluding(@Param("salleId") Long salleId,
													   @Param("start") LocalDateTime start,
													   @Param("end") LocalDateTime end,
													   @Param("excludeId") Long excludeId);

	@Query("select e from EmploiDuTemps e where e.enseignant.id = :enseignantId and e.id <> :excludeId and e.dateDebut < :end and e.dateFin > :start")
	List<EmploiDuTemps> findOverlappingForEnseignantExcluding(@Param("enseignantId") Long enseignantId,
															@Param("start") LocalDateTime start,
															@Param("end") LocalDateTime end,
															@Param("excludeId") Long excludeId);
}

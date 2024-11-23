package com.devocean.Balbalm.mission.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devocean.Balbalm.mission.domain.entity.TreasureMission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TreasureMissionRepository extends JpaRepository<TreasureMission, Long> {
	Optional<TreasureMission> findByUserIdAndLocationMissionId(String userId, Long locationMissionId);
	@Query("SELECT m FROM TreasureMission m WHERE m.userId = :userId AND m.locationMission.id IN :locationMissionIds")
	List<TreasureMission> findAllByUserIdAndLocationMissionIds(@Param(("userId")) String userId, @Param("locationMissionIds") List<Long> locationMissionIds);
}

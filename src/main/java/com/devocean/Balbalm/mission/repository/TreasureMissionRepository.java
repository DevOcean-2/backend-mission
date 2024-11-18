package com.devocean.Balbalm.mission.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devocean.Balbalm.mission.domain.entity.TreasureMission;

public interface TreasureMissionRepository extends JpaRepository<TreasureMission, Long> {
	Optional<TreasureMission> findByUserIdAndLocationMissionId(String userId, Long locationMissionId);
}

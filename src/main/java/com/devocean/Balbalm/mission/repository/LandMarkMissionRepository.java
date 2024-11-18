package com.devocean.Balbalm.mission.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devocean.Balbalm.mission.domain.entity.LandMarkMission;
import com.devocean.Balbalm.mission.domain.entity.TreasureMission;

public interface LandMarkMissionRepository extends JpaRepository<LandMarkMission, Long> {
	LandMarkMission findByUserIdAndLocationMissionId(String userId, Long locationMissionId);
}

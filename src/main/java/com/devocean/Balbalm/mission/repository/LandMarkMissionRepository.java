package com.devocean.Balbalm.mission.repository;

import java.util.List;

import com.devocean.Balbalm.mission.repository.custom.LandMarkMissionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devocean.Balbalm.mission.domain.entity.LandMarkMission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LandMarkMissionRepository extends JpaRepository<LandMarkMission, Long>, LandMarkMissionRepositoryCustom {
	LandMarkMission findByUserIdAndLocationMissionId(String userId, Long locationMissionId);

    @Query("SELECT m FROM LandMarkMission m WHERE m.userId = :userId AND m.locationMission.id IN :locationMissionIds")
    List<LandMarkMission> findAllByUserIdAndLocationMissionIds(@Param(("userId")) String userId, @Param("locationMissionIds") List<Long> locationMissionIds);

}

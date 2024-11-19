package com.devocean.Balbalm.mission.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devocean.Balbalm.mission.domain.entity.LocationMission;
import com.devocean.Balbalm.mission.domain.enumeration.MissionType;

public interface LocationMissionRepository extends JpaRepository<LocationMission, Long> {
	@Query("SELECT m FROM LocationMission m WHERE :currentDate BETWEEN m.startDate AND m.endDate")
	List<LocationMission> findByCurrentDate(@Param("currentDate") LocalDate currentDate);
//	@Query("SELECT m FROM LocationMission m WHERE m.missionType = :missionType AND :currentDate BETWEEN m.startDate AND m.endDate")
//	LocationMission findByMissionTypeAndCurrentDate(@Param("currentDate") LocalDate currentDate, @Param("missionType") MissionType missionType);
	@Query("SELECT m FROM LocationMission m WHERE m.missionType = :missionType AND :currentDate BETWEEN m.startDate AND m.endDate")
	List<LocationMission> findByMissionTypeAndCurrentDate(@Param("currentDate") LocalDate currentDate, @Param("missionType") MissionType missionType);

	@Query("SELECT m FROM LocationMission m WHERE :currentDate BETWEEN m.startDate AND m.endDate AND m.completeAssign = :completeAssign ")
	List<LocationMission> findByCurrentDateAndCompleteAssign(@Param("currentDate") LocalDate currentDate, @Param("completeAssign") boolean completeAssign);

	@Query("UPDATE LocationMission m SET m.completeAssign = true WHERE m.id IN missionIds")
	void updateMissionCompleteAssign(@Param("missionIds") List<Long> missionIds);
}

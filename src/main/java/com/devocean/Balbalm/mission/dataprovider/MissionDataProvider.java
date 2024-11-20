package com.devocean.Balbalm.mission.dataprovider;

import java.time.LocalDate;
import java.util.List;

import com.devocean.Balbalm.mission.domain.UserMissionInfo;
import com.devocean.Balbalm.mission.domain.enumeration.MissionType;
import com.devocean.Balbalm.mission.domain.MissionInfo;

public interface MissionDataProvider {
	List<UserMissionInfo> getMissionList(String userId, LocalDate today);
	List<UserMissionInfo> getUserMissionInfoList(String userId, MissionType missionType);
	List<MissionInfo> getMissionInfoList(LocalDate today, MissionType missionType);
	UserMissionInfo getUserMissionInfo(String userId, MissionType missionType, Long missionId);
	double getMissionDistance(double currentLatitude, double currentLongitude, double targetLatitude, double targetLongitude);
	void updateLandMarkMissionCount(String userId, Long missionId);
	void completeTreasureMission(String userId, Long missionId);
	// 이달의 새로운 미션 조회
	List<MissionInfo> getNewMissionList(LocalDate today);
	void updateNewMissionList(List<Long> locationMissionIds);
	void assignNewTreasureHuntMission(Long missionId, List<String> userIds);
	void assignNewLandMarkMission(Long missionId, List<String> userIds);
	void completeAssignNewMission(List<Long> missionIds);
}

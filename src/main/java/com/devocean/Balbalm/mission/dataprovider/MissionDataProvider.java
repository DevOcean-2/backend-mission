package com.devocean.Balbalm.mission.dataprovider;

import java.time.LocalDate;
import java.util.List;

import com.devocean.Balbalm.mission.domain.UserMissionInfo;
import com.devocean.Balbalm.mission.domain.enumeration.MissionType;
import com.devocean.Balbalm.mission.domain.MissionInfo;
import com.devocean.Balbalm.notification.domain.enumeration.NotificationType;

public interface MissionDataProvider {
	List<UserMissionInfo> getMissionList(String userId, LocalDate today);
	MissionInfo getMissionInfo(String userId, NotificationType notificationType);
	MissionInfo getMissionInfo(LocalDate today, MissionType missionType);
	UserMissionInfo getUserMissionInfo(String userId, MissionType missionType, Long missionId);
	double getMissionDistance(double currentLatitude, double currentLongitude, double targetLatitude, double targetLongitude);
	void updateLandMarkMissionCount(String userId, Long missionId);
	void completeTreasureMission(String userId, Long missionId);
}

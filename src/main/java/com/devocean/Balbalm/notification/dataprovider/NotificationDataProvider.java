package com.devocean.Balbalm.notification.dataprovider;

import java.util.List;

import com.devocean.Balbalm.mission.domain.enumeration.MissionType;
import com.devocean.Balbalm.notification.domain.NotificationInfo;

public interface NotificationDataProvider {
	List<NotificationInfo> getNotificationList(String userId);
	void saveNotification(String userId, MissionType missionType, Long missionId, String locationName, String missionName, int percent, boolean isCompleted);
}

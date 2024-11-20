package com.devocean.Balbalm.notification.dataprovider;

import java.util.List;

import com.devocean.Balbalm.notification.domain.NotificationInfo;

public interface NotificationDataProvider {
	List<NotificationInfo> getNotificationList(String userId);
}

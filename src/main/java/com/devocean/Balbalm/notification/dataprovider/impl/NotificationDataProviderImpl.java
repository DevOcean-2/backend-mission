package com.devocean.Balbalm.notification.dataprovider.impl;

import java.util.List;

import com.devocean.Balbalm.mission.domain.enumeration.MissionType;
import com.devocean.Balbalm.notification.dataprovider.NotificationDataProvider;
import com.devocean.Balbalm.notification.domain.NotificationInfo;
import com.devocean.Balbalm.notification.domain.entity.Notification;
import com.devocean.Balbalm.notification.domain.entity.NotificationContent;
import com.devocean.Balbalm.notification.domain.enumeration.NotificationStatus;
import com.devocean.Balbalm.notification.domain.enumeration.NotificationType;
import com.devocean.Balbalm.notification.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationDataProviderImpl implements NotificationDataProvider {
	private final NotificationRepository notificationRepository;

	@Override
	public List<NotificationInfo> getNotificationList(String userId) {
		List<Notification> notificationList = notificationRepository.findAllByToUserIdOrderByIdDesc(userId);
		return NotificationDataProviderImplMapper.MAPPER.toNotificationInfoList(notificationList);
	}

	@Override
	public void saveNotification(String userId, MissionType missionType, Long missionId, String locationName, String missionName, int percent, boolean isCompleted) {

		Notification notification = Notification.builder()
				.toUserId(userId)
				.notificationType(NotificationType.MISSION)
				.notificationContent(
						NotificationContent.builder()
								.missionId(missionId)
								.missionType(missionType)
								.locationName(locationName)
								.missionName(missionName)
								.percent(percent)
								.isCompleted(isCompleted)
								.build()
				)
				.notificationStatus(NotificationStatus.UNREAD)
				.build();
		notificationRepository.save(notification);
	}

	@Mapper
	public interface NotificationDataProviderImplMapper {
		NotificationDataProviderImpl.NotificationDataProviderImplMapper MAPPER = Mappers.getMapper(NotificationDataProviderImpl.NotificationDataProviderImplMapper.class);
		List<NotificationInfo> toNotificationInfoList(List<Notification> notificationList);
	}
}

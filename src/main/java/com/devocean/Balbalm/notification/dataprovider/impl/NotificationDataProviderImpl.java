package com.devocean.Balbalm.notification.dataprovider.impl;

import java.util.List;

import com.devocean.Balbalm.notification.dataprovider.NotificationDataProvider;
import com.devocean.Balbalm.notification.domain.NotificationInfo;
import com.devocean.Balbalm.notification.domain.entity.Notification;
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
		List<Notification> notificationList = notificationRepository.findAllByToUserId(userId);
		return NotificationDataProviderImplMapper.MAPPER.toNotificationInfoList(notificationList);
	}

	@Mapper
	public interface NotificationDataProviderImplMapper {
		NotificationDataProviderImpl.NotificationDataProviderImplMapper MAPPER = Mappers.getMapper(NotificationDataProviderImpl.NotificationDataProviderImplMapper.class);
		List<NotificationInfo> toNotificationInfoList(List<Notification> notificationList);
	}
}

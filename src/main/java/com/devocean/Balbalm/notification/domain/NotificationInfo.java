package com.devocean.Balbalm.notification.domain;

import com.devocean.Balbalm.notification.domain.entity.NotificationContent;
import com.devocean.Balbalm.notification.domain.enumeration.NotificationStatus;
import com.devocean.Balbalm.notification.domain.enumeration.NotificationType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationInfo {

	private Long id;
	private String toUserId;
	private NotificationType notificationType;
	private NotificationContent notificationContent;
	private NotificationStatus notificationStatus;
}

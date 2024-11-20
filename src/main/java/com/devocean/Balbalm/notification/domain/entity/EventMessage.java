package com.devocean.Balbalm.notification.domain.entity;

import com.devocean.Balbalm.mission.domain.enumeration.MissionType;
import com.devocean.Balbalm.notification.domain.enumeration.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventMessage<T> {
	// private NotificationType type;
	private MissionType type;
	private T message;
}

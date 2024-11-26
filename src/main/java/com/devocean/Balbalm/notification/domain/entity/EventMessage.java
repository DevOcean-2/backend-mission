package com.devocean.Balbalm.notification.domain.entity;

import com.devocean.Balbalm.mission.domain.enumeration.MissionType;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventMessage<T> {
	private MissionType type;
	private T message;
}

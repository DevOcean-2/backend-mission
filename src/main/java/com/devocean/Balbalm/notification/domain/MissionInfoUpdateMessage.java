package com.devocean.Balbalm.notification.domain;

import com.devocean.Balbalm.mission.domain.MissionInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionInfoUpdateMessage {
	private MissionInfo missionInfo;
}

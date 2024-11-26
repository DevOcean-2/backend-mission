package com.devocean.Balbalm.notification.domain;

import com.devocean.Balbalm.mission.domain.UserMissionInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectMessage {
	private String userId;
	List<UserMissionInfo> userMissionInfoList;
}
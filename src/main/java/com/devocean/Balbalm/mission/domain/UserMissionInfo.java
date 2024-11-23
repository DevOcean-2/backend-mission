package com.devocean.Balbalm.mission.domain;

import java.time.LocalDate;

import com.devocean.Balbalm.mission.domain.enumeration.MissionProgressType;
import com.devocean.Balbalm.mission.domain.enumeration.MissionType;
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
public class UserMissionInfo {
	private Long missionId;
	private Long userMissionId;

	private MissionType missionType;
	private String locationName;
	private String missionName;

	private int count;
	private int percent;
	private MissionProgressType missionProgressType;
	private boolean isComplete;
	private LocalDate completeDate;
}

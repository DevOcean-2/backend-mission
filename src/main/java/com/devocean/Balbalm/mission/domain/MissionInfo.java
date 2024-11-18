package com.devocean.Balbalm.mission.domain;

import java.time.LocalDate;

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
public class MissionInfo {
	private Long missionId;
	private MissionType missionType;
	private String locationName;
	private String missionName;

	private String description;
	private int targetCount;

	private double latitude;
	private double longitude;

	private LocalDate startDate;
	private LocalDate endDate;
}

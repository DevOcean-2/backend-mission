package com.devocean.Balbalm.feedMission.controller.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionResponseDto {
	private String mission;
	private List<String> hashtag;
	private int month;
	private int year;
	private Long missionId;
}

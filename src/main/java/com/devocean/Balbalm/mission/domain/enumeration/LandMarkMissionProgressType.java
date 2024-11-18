package com.devocean.Balbalm.mission.domain.enumeration;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum LandMarkMissionProgressType {
	ZERO(0, 0, ""),
	FIRST(1, 30, ""),
	SECOND(2, 60, ""),
	THIRD(3, 100, "");;

	private final int count;
	private final int percent;
	private final String message;

	LandMarkMissionProgressType(int count, int percent, String message) {
		this.count = count;
		this.percent = percent;
		this.message = message;
	}

	public static LandMarkMissionProgressType findProgressType(int count) {
		return Arrays.stream(values())
			.filter(progressType -> progressType.count == count)
			.findFirst()
			.orElse(ZERO);
	}
}

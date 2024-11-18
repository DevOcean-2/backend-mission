package com.devocean.Balbalm.mission.domain.enumeration;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum TreasureHuntMissionProgressType {
	NONE(10000, 0, "보물을 찾아주세요!"),
	ONE_THOUSAND(1000, 0, "1km 이내에 보물이 있어요!"),
	FIVE_HUNDRED(500, 30, "500m 이내에 보물이 있어요!"),
	TWO_HUNDRED(200, 60, "200m 이내에 보물이 있어요!"),
	HUNDRED(0, 100, "");

	private final int distance;
	private final int percent;
	private final String message;

	TreasureHuntMissionProgressType(int distance, int percent, String message) {
		this.distance = distance;
		this.percent = percent;
		this.message = message;
	}

	public TreasureHuntMissionProgressType findProgressType(int distance) {
		return Arrays.stream(values())
			.filter(progressType -> progressType.distance == distance)
			.findFirst()
			.orElse(ONE_THOUSAND);
	}
}

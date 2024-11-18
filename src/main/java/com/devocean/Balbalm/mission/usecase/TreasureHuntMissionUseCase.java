package com.devocean.Balbalm.mission.usecase;

import static com.devocean.Balbalm.global.enumeration.ResultCode.ALREADY_COMPLETE_MISSION;
import static com.devocean.Balbalm.mission.domain.enumeration.TreasureHuntMissionProgressType.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.devocean.Balbalm.global.exception.CommonException;
import com.devocean.Balbalm.mission.domain.UserMissionInfo;
import org.springframework.stereotype.Component;

import com.devocean.Balbalm.global.UseCase;
import com.devocean.Balbalm.global.util.JwtUtil;
import com.devocean.Balbalm.mission.dataprovider.MissionDataProvider;
import com.devocean.Balbalm.mission.domain.enumeration.MissionProgressType;
import com.devocean.Balbalm.mission.domain.enumeration.MissionType;
import com.devocean.Balbalm.mission.domain.MissionInfo;
import com.devocean.Balbalm.mission.domain.enumeration.TreasureHuntMissionProgressType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TreasureHuntMissionUseCase implements UseCase<TreasureHuntMissionUseCase.Command, TreasureHuntMissionUseCase.Result> {
	private final MissionDataProvider missionDataProvider;
	private final JwtUtil jwtUtil;
	@Override
	public Result execute(Command input) {
		String userId = jwtUtil.extractSocialId(input.getToken());
		LocalDate today = LocalDate.now();
		double currentLatitude = input.getLatitude();
		double currentLongitude = input.getLongitude();

		MissionType missionType = input.getMissionType();
		MissionInfo missionInfo = missionDataProvider.getMissionInfo(today, missionType);
		Long missionId = missionInfo.getMissionId();

		UserMissionInfo userMissionInfo = missionDataProvider.getUserMissionInfo(userId, missionType, missionId);
		if (userMissionInfo.isComplete()) {
			throw new CommonException(ALREADY_COMPLETE_MISSION);
		}

		double distance = missionDataProvider.getMissionDistance(currentLatitude, currentLongitude, missionInfo.getLatitude(), missionInfo.getLongitude());
		int calDistance = (int) distance;

		TreasureHuntMissionProgressType progressType = NONE;
		boolean isComplete = false;

		if (calDistance <= HUNDRED.getDistance()) {
			isComplete = true;
			progressType = HUNDRED;
		} else if (calDistance <= TWO_HUNDRED.getDistance()) {
			progressType = TWO_HUNDRED;
		} else if (calDistance <= FIVE_HUNDRED.getDistance()) {
			progressType = FIVE_HUNDRED;
		} else if (calDistance <= ONE_THOUSAND.getDistance()) {
			progressType = ONE_THOUSAND;
		}
		int percent = progressType.getPercent();

		// 방문 성공인 경우 방문 성공 횟수를 기록
		MissionProgressType missionProgressType = MissionProgressType.PROGRESS;
		if (isComplete) {
			missionProgressType = MissionProgressType.COMPLETE;
			missionDataProvider.completeTreasureMission(userId, missionId);
		}

		return Result.builder()
			.isComplete(isComplete)
			.missionProgressType(missionProgressType)
			.percent(percent)
			.message(progressType.getMessage())
			.distance(distance)
			.build();
	}

	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Command implements Serializable, UseCase.Command {
		private String token;
		private MissionType missionType;
		private double latitude;
		private double longitude;
	}

	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Result implements Serializable, UseCase.Result {
		private boolean isComplete;   // 완료 여부
		private MissionProgressType missionProgressType;   // 미션 진행 상태
		private int percent;
		private String message;
		private double distance;
	}
}

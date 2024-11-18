package com.devocean.Balbalm.mission.usecase;

import static com.devocean.Balbalm.global.enumeration.ResultCode.ALREADY_COMPLETE_MISSION;
import static com.devocean.Balbalm.mission.domain.enumeration.LandMarkMissionProgressType.*;
import static com.devocean.Balbalm.mission.domain.enumeration.TreasureHuntMissionProgressType.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.devocean.Balbalm.global.exception.CommonException;
import org.springframework.stereotype.Component;

import com.devocean.Balbalm.global.UseCase;
import com.devocean.Balbalm.global.util.JwtUtil;
import com.devocean.Balbalm.mission.dataprovider.MissionDataProvider;
import com.devocean.Balbalm.mission.domain.UserMissionInfo;
import com.devocean.Balbalm.mission.domain.enumeration.LandMarkMissionProgressType;
import com.devocean.Balbalm.mission.domain.enumeration.MissionProgressType;
import com.devocean.Balbalm.mission.domain.enumeration.MissionType;
import com.devocean.Balbalm.mission.domain.MissionInfo;
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
public class LandMarkMissionUseCase implements UseCase<LandMarkMissionUseCase.Command, LandMarkMissionUseCase.Result> {
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

		UserMissionInfo userMissionInfo = missionDataProvider.getUserMissionInfo(userId, missionType, missionInfo.getMissionId());
		if (userMissionInfo.isComplete()) {
			throw new CommonException(ALREADY_COMPLETE_MISSION);
		}

		double distance = missionDataProvider.getMissionDistance(currentLatitude, currentLongitude, missionInfo.getLatitude(), missionInfo.getLongitude());
		int calDistance = (int) distance;

		LandMarkMissionProgressType progressType = LandMarkMissionProgressType.ZERO;
		boolean isComplete = false;

		if (calDistance <= HUNDRED.getDistance()) {
			isComplete = true;
		} else if (calDistance <= TWO_HUNDRED.getDistance()) {

		} else if (calDistance <= FIVE_HUNDRED.getDistance()) {

		} else {

		}

		// 방문 성공인 경우 방문 성공 횟수를 기록
		MissionProgressType missionProgressType = MissionProgressType.PROGRESS;
		if (isComplete) {
			missionDataProvider.updateLandMarkMissionCount(userId, missionInfo.getMissionId());
		}

		UserMissionInfo updateUserMissionInfo = missionDataProvider.getUserMissionInfo(userId, missionType, missionId);

		return Result.builder()
			.isComplete(userMissionInfo.isComplete())
			.missionProgressType(userMissionInfo.getMissionProgressType())
			.percent(userMissionInfo.getPercent())
			.count(userMissionInfo.getCount())
			.targetCount(missionInfo.getTargetCount())
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
		private boolean isComplete;
		private MissionProgressType missionProgressType;
		private int count;   // 방문 성공 횟수
		private int targetCount;   // 미션 성공을 위한 방문 횟수
		private int percent;
		private double distance;
	}
}
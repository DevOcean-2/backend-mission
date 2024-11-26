package com.devocean.Balbalm.mission.usecase;

import static com.devocean.Balbalm.mission.domain.enumeration.TreasureHuntMissionProgressType.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.devocean.Balbalm.mission.domain.UserMissionInfo;
import com.devocean.Balbalm.notification.dataprovider.NotificationDataProvider;
import com.devocean.Balbalm.notification.service.NotificationService;
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
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TreasureHuntMissionUseCase implements UseCase<TreasureHuntMissionUseCase.Command, TreasureHuntMissionUseCase.Result> {

	private final MissionDataProvider missionDataProvider;
	private final NotificationDataProvider notificationDataProvider;
	private final NotificationService notificationService;
	private final JwtUtil jwtUtil;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Result execute(Command input) {
		String userId = jwtUtil.extractSocialId(input.getToken());
		LocalDate today = LocalDate.now();
		double currentLatitude = input.getLatitude();
		double currentLongitude = input.getLongitude();

		MissionType missionType = input.getMissionType();
		List<MissionInfo> missionInfoList = missionDataProvider.getMissionInfoList(today, missionType);

		List<Result.Mission> missionList = new ArrayList<>();
		for (MissionInfo missionInfo : missionInfoList) {
			Long missionId = missionInfo.getMissionId();

			UserMissionInfo userMissionInfo = missionDataProvider.getUserMissionInfo(userId, missionType, missionId);
//			if (userMissionInfo.isComplete()) {
//				throw new CommonException(ALREADY_COMPLETE_MISSION);
//			}

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
				
				// 미션 알림 쌓기
				notificationDataProvider.saveNotification(userId, MissionType.TREASURE_HUNT, missionId,
						userMissionInfo.getLocationName(), userMissionInfo.getMissionName(), userMissionInfo.getPercent(), true);

				// 알림 전송
				notificationService.sendTreasureHuntInfo(userId);
			}

			missionList.add(
					Result.Mission.builder()
							.isComplete(isComplete)
							.missionProgressType(missionProgressType)
							.percent(percent)
							.message(progressType.getMessage())
							.distance(distance)
							.build()
			);
		}

		return Result.builder()
				.missionList(missionList)
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
		private List<Mission> missionList;

		@Getter
		@Setter
		@Builder
		@NoArgsConstructor
		@AllArgsConstructor
		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Mission {
			private boolean isComplete;   // 완료 여부
			private MissionProgressType missionProgressType;   // 미션 진행 상태
			private int percent;
			private String message;
			private double distance;
		}
	}
}

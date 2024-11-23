package com.devocean.Balbalm.mission.usecase;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.devocean.Balbalm.global.UseCase;
import com.devocean.Balbalm.global.util.JwtUtil;
import com.devocean.Balbalm.mission.dataprovider.MissionDataProvider;
import com.devocean.Balbalm.mission.domain.UserMissionInfo;
import com.devocean.Balbalm.mission.domain.enumeration.MissionProgressType;
import com.devocean.Balbalm.mission.domain.enumeration.MissionType;
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
public class GetMissionListUseCase implements UseCase<GetMissionListUseCase.Command, GetMissionListUseCase.Result> {
	private final MissionDataProvider missionDataProvider;
	private final JwtUtil jwtUtil;

	@Override
	public Result execute(Command input) {
		LocalDate today = LocalDate.now();
		String userId = jwtUtil.extractSocialId(input.getToken());
		List<UserMissionInfo> missionList = missionDataProvider.getMissionList(userId, today);

		return Result.builder()
			.missionList(GetMissionListUseCaseMapper.MAPPER.toResult(missionList))
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
		@NoArgsConstructor
		@AllArgsConstructor
		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Mission {
			private Long missionId;
			private Long userMissionId;

			private String missionName;
			private MissionType missionType;

			private int count;
			private String percent;
			private MissionProgressType missionProgressType;
			private boolean isComplete;
			private LocalDate completeDate;
		}
	}

	@Mapper
	public interface GetMissionListUseCaseMapper {
		GetMissionListUseCase.GetMissionListUseCaseMapper MAPPER = Mappers.getMapper(GetMissionListUseCaseMapper.class);
		List<Result.Mission> toResult(List<UserMissionInfo> userMissionInfoList);
	}
}

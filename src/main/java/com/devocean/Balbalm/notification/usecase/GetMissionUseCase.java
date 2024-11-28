package com.devocean.Balbalm.notification.usecase;

import com.devocean.Balbalm.global.UseCase;
import com.devocean.Balbalm.mission.dataprovider.MissionDataProvider;
import com.devocean.Balbalm.mission.domain.UserMissionInfo;
import com.devocean.Balbalm.mission.domain.enumeration.MissionProgressType;
import com.devocean.Balbalm.mission.domain.enumeration.MissionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class GetMissionUseCase implements UseCase<GetMissionUseCase.Command, GetMissionUseCase.Result> {

    private final MissionDataProvider missionDataProvider;

    @Override
    public Result execute(Command input) {
        String userId = input.getUserId();
        List<UserMissionInfo> userMissionInfoList = missionDataProvider.getUserMissionInfoList(userId, input.getMissionType());
        return Result.builder()
                .userId(userId)
                .userMissionInfoList(GetMissionUseCaseMapper.MAPPER.toResult(userMissionInfoList))
                .build();
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Command implements Serializable, UseCase.Command {

        @Serial
        private static final long serialVersionUID = 7227058212102302982L;
        private String userId;
        private MissionType missionType;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result implements Serializable, UseCase.Result {

        @Serial
        private static final long serialVersionUID = 6451279481135605141L;

        private String userId;
        private List<Mission> userMissionInfoList;

        @Getter
        @Setter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Mission {
            private Long missionId;
            private Long userMissionId;

            private MissionType missionType;
            private String locationName;
            private String missionName;
            private double latitude;
            private double longitude;

            private int count;
            private int percent;
            private MissionProgressType missionProgressType;
            private boolean isComplete;
            private LocalDate completeDate;
        }
    }

    @Mapper
    public interface GetMissionUseCaseMapper {
        GetMissionUseCase.GetMissionUseCaseMapper MAPPER = Mappers.getMapper(GetMissionUseCase.GetMissionUseCaseMapper.class);

        List<Result.Mission> toResult(List<UserMissionInfo> userMissionInfoList);
    }
}
package com.devocean.Balbalm.mission.usecase;

import com.devocean.Balbalm.global.UseCase;
import com.devocean.Balbalm.mission.dataprovider.MissionDataProvider;
import com.devocean.Balbalm.mission.dataprovider.UserDataProvider;
import com.devocean.Balbalm.mission.domain.MissionInfo;
import com.devocean.Balbalm.mission.domain.UserInfo;
import com.devocean.Balbalm.mission.domain.enumeration.MissionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AssignUpdateMissionUseCase implements UseCase<AssignUpdateMissionUseCase.Command, AssignUpdateMissionUseCase.Result> {

    private final MissionDataProvider missionDataProvider;
    private final UserDataProvider userDataProvider;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result execute(Command input) {
        List<MissionInfo> newMissionList = missionDataProvider.getNewMissionList(LocalDate.now());

        List<UserInfo> userInfoList = userDataProvider.getAllUserList();
        List<String> userIds = userInfoList.stream().map(UserInfo::getUserId).toList();

        // 각 미션마다 돌면서, 모든 유저들에게 새로운 미션 할당 처리
        for (MissionInfo missionInfo : newMissionList) {
            Long locationMissionId = missionInfo.getMissionId();
            MissionType missionType = missionInfo.getMissionType();
            if (MissionType.TREASURE_HUNT.equals(missionType)) {
                missionDataProvider.assignNewTreasureHuntMission(locationMissionId, userIds);
            } else if (MissionType.LANDMARK.equals(missionType)) {
                missionDataProvider.assignNewLandMarkMission(locationMissionId, userIds);
            }
        }

        // 미션 할당 완료 처리
        missionDataProvider.completeAssignNewMission(newMissionList.stream().map(MissionInfo::getMissionId).toList());
        return new Result();
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Command implements Serializable, UseCase.Command {

        @Serial
        private static final long serialVersionUID = 2588341412630498246L;
        private LocalDate now;
    }


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result implements Serializable, UseCase.Result {

        @Serial
        private static final long serialVersionUID = -4946268366540850547L;
    }
}

package com.devocean.Balbalm.mission.scheduler;

import com.devocean.Balbalm.mission.usecase.AssignUpdateMissionUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class MissionScheduler {

    private final AssignUpdateMissionUseCase assignUpdateMissionUseCase;

    /**
     * 월초 새로운 미션 정보를 유저에게 할당 -> treasureMission, landmarkMission 에 정보 추가
     */
//    @Scheduled(cron = "*/20 * * * * *")
    public void sendTreasureHuntInfo() {
        log.info("================= start assign new mission for all users =================");
        LocalDate now = LocalDate.now();
        assignUpdateMissionUseCase.execute(new AssignUpdateMissionUseCase.Command(now));
        log.info("================= end assign new mission for all user =================");
    }
}

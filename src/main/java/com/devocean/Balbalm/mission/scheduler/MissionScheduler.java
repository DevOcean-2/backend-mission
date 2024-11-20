package com.devocean.Balbalm.mission.scheduler;

import com.devocean.Balbalm.mission.usecase.AssignUpdateMissionUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class MissionScheduler {

    private final AssignUpdateMissionUseCase assignUpdateMissionUseCase;

    /**
     * 하루 주기로 새로운 미션 정보를 유저에게 할당 -> treasureMission, landmarkMission 에 정보 추가
     */
//    @Scheduled(cron = "0 12 * * * *")
    public void sendTreasureHuntInfo() {
        log.info("================= start assign new mission for all users =================");
        LocalDate now = LocalDate.now();
        assignUpdateMissionUseCase.execute(new AssignUpdateMissionUseCase.Command(now));
        log.info("================= end assign new mission for all user =================");
    }
}

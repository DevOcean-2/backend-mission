package com.devocean.Balbalm.notification.scheduler;

import com.devocean.Balbalm.mission.dataprovider.MissionDataProvider;
import com.devocean.Balbalm.notification.repository.EmitterRepository;
import com.devocean.Balbalm.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationScheduler {
    private final MissionDataProvider missionDataProvider;
    private final NotificationService notificationService;
    private final EmitterRepository emitterRepository;

    /**
     * 보물찾기 미션 정보를 지속적으로 전달
     */
    @Scheduled(cron = "*/20 * * * * *")
    public void sendTreasureHuntInfo() {
        log.info("================= start send treasure hunt info =================");
        Set<String> keys = emitterRepository.findAllUserKey();
        // TODO 보물찾기 미션을 완료한 유저(userId)에게 알림 발송
        keys.forEach(notificationService::sendTreasureHuntInfo);
        log.info("================= end send treasure hunt info =================");
    }

    /**
     * 랜드마크 방문 미션 정보를 지속적으로 전달
     */
    @Scheduled(cron = "*/20 * * * * *")
    public void sendLandMarkVisitInfo() {
        log.info("================= start send landmark visit info =================");
        Set<String> keys = emitterRepository.findAllUserKey();
        // TODO 랜드마크 방문 미션을 완료한 유저(userId)에게 알림 발송
        keys.forEach(notificationService::sendLandMarkInfo);
        log.info("================= end send landmark visit info =================");
    }
}
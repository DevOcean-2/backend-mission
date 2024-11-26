package com.devocean.Balbalm.notification.service;

import com.devocean.Balbalm.global.enumeration.ResultCode;
import com.devocean.Balbalm.global.exception.CommonException;
import com.devocean.Balbalm.mission.dataprovider.MissionDataProvider;
import com.devocean.Balbalm.mission.domain.UserMissionInfo;
import com.devocean.Balbalm.mission.domain.enumeration.MissionType;
import com.devocean.Balbalm.notification.domain.ConnectMessage;
import com.devocean.Balbalm.notification.domain.MissionInfoUpdateMessage;
import com.devocean.Balbalm.notification.domain.entity.EmitterInfo;
import com.devocean.Balbalm.notification.domain.entity.EventMessage;
import com.devocean.Balbalm.notification.repository.EmitterRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;   // 기본 타임아웃 60분
    private final EmitterRepository emitterRepository;
    private final MissionDataProvider missionDataProvider;

    @Transactional
    public SseEmitter subscribe(String userId, MissionType missionType) {
        ConnectMessage connectMessage = connect(userId, missionType);
        sendMessage(userId, new EventMessage(missionType, connectMessage));

        EmitterInfo emitterInfo = emitterRepository.get(userId);

        if (ObjectUtils.isEmpty(emitterInfo)) {
            throw new CommonException(ResultCode.UNKNOWN_SERVER_ERROR);
        }

        return emitterInfo.getEmitter();
    }

    private ConnectMessage connect(String userId, MissionType missionType) {
        List<UserMissionInfo> userMissionInfoList = missionDataProvider.getUserMissionInfoList(userId, missionType);

        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        EmitterInfo emitterInfo = new EmitterInfo(userId, emitter);

        emitterRepository.save(userId, emitterInfo);

        emitter.onCompletion(() -> emitterRepository.deleteById(userId));
        emitter.onTimeout(() -> emitterRepository.deleteById(userId));

        return new ConnectMessage(userId, userMissionInfoList);
    }

    @Transactional
    public void unsubscribe(String userId, MissionType missionType) {
        complete(userId, missionType);
    }

    private void sendMessage(final String userId, final EventMessage eventMessage) {
        EmitterInfo emitterInfo = emitterRepository.get(userId);

        if (!ObjectUtils.isEmpty(emitterInfo)) {
            try {
                emitterInfo.getEmitter().send(
                        SseEmitter.event().id(userId).name(eventMessage.getType().toString()).data(eventMessage)
                );
            } catch (IOException exception) {
                emitterRepository.deleteById(userId);
                emitterInfo.getEmitter().completeWithError(exception);
            }
        }
    }

    private void complete(final String userId, final MissionType missionType) {
        EmitterInfo emitterInfo = emitterRepository.get(userId);
        EventMessage<Void> eventMessage = new EventMessage<>(missionType, null);

        if (emitterInfo != null) {
            try {
                emitterInfo.getEmitter().send(
                        SseEmitter.event().id(userId).name(eventMessage.getType().toString()).data(eventMessage)
                );
            } catch (IOException exception) {
                emitterInfo.getEmitter().completeWithError(exception);
            }

            emitterRepository.deleteById(userId);
            emitterInfo.getEmitter().complete();
        }
    }

    @Async
    @Transactional(readOnly = true)
    public void sendTreasureHuntInfo(final String userId) {
        EmitterInfo emitterInfo = emitterRepository.get(userId);

        if (!ObjectUtils.isEmpty(emitterInfo)) {
            try {
                MissionType type = MissionType.TREASURE_HUNT;
                List<UserMissionInfo> userMissionInfoList = missionDataProvider.getUserMissionInfoList(userId, type);
                sendMessage(userId,
                        new EventMessage(type,
                                new MissionInfoUpdateMessage(userMissionInfoList)));
                userMissionInfoList.forEach(userMissionInfo -> log.info("update missionInfo name {}", userMissionInfo.getMissionName()));
            } catch (Exception e) {
                unsubscribe(userId, MissionType.TREASURE_HUNT);
            }
        }
    }

    @Async
    @Transactional
    public void sendLandMarkInfo(final String userId) {
        EmitterInfo emitterInfo = emitterRepository.get(userId);

        if (!ObjectUtils.isEmpty(emitterInfo)) {
            try {
                MissionType type = MissionType.LANDMARK;
                List<UserMissionInfo> userMissionInfoList = missionDataProvider.getUserMissionInfoList(userId, type);
                sendMessage(userId,
                        new EventMessage(type,
                                new MissionInfoUpdateMessage(userMissionInfoList)));
                userMissionInfoList.forEach(userMissionInfo -> log.info(")update missionInfo name {}", userMissionInfo.getMissionName()));
            } catch (Exception e) {
                unsubscribe(userId, MissionType.LANDMARK);
            }
        }
    }
}
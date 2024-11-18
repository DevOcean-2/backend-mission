package com.devocean.Balbalm.notification.service;

import com.devocean.Balbalm.global.enumeration.ResultCode;
import com.devocean.Balbalm.global.exception.CommonException;
import com.devocean.Balbalm.mission.dataprovider.MissionDataProvider;
import com.devocean.Balbalm.mission.domain.enumeration.MissionType;
import com.devocean.Balbalm.notification.domain.ConnectMessage;
import com.devocean.Balbalm.mission.domain.MissionInfo;
import com.devocean.Balbalm.notification.domain.MissionInfoUpdateMessage;
import com.devocean.Balbalm.notification.domain.entity.EmitterInfo;
import com.devocean.Balbalm.notification.domain.entity.EventMessage;
import com.devocean.Balbalm.notification.domain.enumeration.NotificationType;
import com.devocean.Balbalm.notification.repository.EmitterRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;   // 기본 타임아웃 60분
    private final EmitterRepository emitterRepository;
    private final MissionDataProvider missionDataProvider;

    @Transactional
    public SseEmitter subscribe(String userId, NotificationType notificationType) {
        ConnectMessage connectMessage = connect(userId, notificationType);
        // MissionInfo missionInfo = missionDataProvider.getMissionInfo(userId, notificationType);
        sendMessage(userId, new EventMessage(notificationType, connectMessage));
        // EmitterInfo emitterInfo = emitterRepository.get(connectMessage.getMissionInfo().getUserId());
        EmitterInfo emitterInfo = emitterRepository.get(userId);

        if (ObjectUtils.isEmpty(emitterInfo)) {
            throw new CommonException(ResultCode.UNKNOWN_SERVER_ERROR);
        }

        return emitterInfo.getEmitter();
    }

    private ConnectMessage connect(String userId, NotificationType notificationType) {

        MissionInfo missionInfo = missionDataProvider.getMissionInfo(userId, notificationType);

        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        EmitterInfo emitterInfo = new EmitterInfo(userId, emitter);

        emitterRepository.save(userId, emitterInfo);

        emitter.onCompletion(() -> emitterRepository.deleteById(userId));
        emitter.onTimeout(() -> emitterRepository.deleteById(userId));

        return new ConnectMessage(userId, missionInfo);
    }

    @Transactional
    public void unsubscribe(String userId) {
        complete(userId);
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

    private void complete(final String userId) {
        EmitterInfo emitterInfo = emitterRepository.get(userId);
        EventMessage<Void> eventMessage = new EventMessage<>(NotificationType.DISCONNECT, null);

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
                NotificationType type = NotificationType.findNotificationType(MissionType.TREASURE_HUNT.name());
                MissionInfo missionInfo = missionDataProvider.getMissionInfo(userId, type);
                sendMessage(userId,
                        new EventMessage(NotificationType.TREASURE_HUNT,
                                new MissionInfoUpdateMessage(missionInfo)));
                log.info("update missionInfo name {}", missionInfo.getMissionName());
            } catch (Exception e) {
                unsubscribe(userId);
            }
        }
    }

    @Async
    @Transactional
    public void sendLandMarkInfo(final String userId) {
        EmitterInfo emitterInfo = emitterRepository.get(userId);

        if (!ObjectUtils.isEmpty(emitterInfo)) {
            try {
                NotificationType type = NotificationType.findNotificationType(MissionType.LANDMARK.name());
                MissionInfo missionInfo = missionDataProvider.getMissionInfo(userId, type);
                sendMessage(userId,
                    new EventMessage(NotificationType.LANDMARK,
                        new MissionInfoUpdateMessage(missionInfo)));
                log.info("update missionInfo name {}", missionInfo.getMissionName());
            } catch (Exception e) {
                unsubscribe(userId);
            }
        }
    }
}
package com.devocean.Balbalm.notification.domain.entity;

import com.devocean.Balbalm.mission.domain.enumeration.MissionType;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** 실시간 미션 알림의 타겟이 되는 필드들 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationContent {

    private Long missionId;
    private MissionType missionType;
    private String message;
    private int percent;
    private boolean isCompleted;
}

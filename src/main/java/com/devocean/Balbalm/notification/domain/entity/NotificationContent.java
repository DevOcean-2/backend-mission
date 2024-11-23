package com.devocean.Balbalm.notification.domain.entity;

import com.devocean.Balbalm.mission.domain.enumeration.MissionType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

/** 실시간 미션 알림의 타겟이 되는 필드들 */
@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NotificationContent {

    private Long missionId;
//    @Enumerated(EnumType.STRING)
    private MissionType missionType;
    private String locationName;
    private String missionName;
    private int percent;
    private boolean isCompleted;
}

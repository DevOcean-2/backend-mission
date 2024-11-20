package com.devocean.Balbalm.mission.domain.dto;

import com.devocean.Balbalm.mission.domain.entity.LocationMission;
import com.devocean.Balbalm.mission.domain.enumeration.MissionProgressType;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LandMarkMissionDto {
    private Long id;
    private LocationMission locationMission;
    private String userId;
    private int count;
    private int percent;
    private MissionProgressType progressType;
    private boolean isComplete;
    private LocalDateTime completeDate;

    @QueryProjection
    public LandMarkMissionDto(Long id, LocationMission locationMission, String userId, int count, int percent,
                              MissionProgressType progressType, boolean isComplete, LocalDateTime completeDate) {
        this.id = id;
        this.locationMission = locationMission;
        this.userId = userId;
        this.count = count;
        this.percent = percent;
        this.progressType = progressType;
        this.isComplete = isComplete;
        this.completeDate = completeDate;
    }
}

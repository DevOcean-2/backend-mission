package com.devocean.Balbalm.mission.domain.enumeration;

import java.util.Arrays;

public enum MissionType {
    TREASURE_HUNT, LANDMARK, FEED;

    public static MissionType findMissionType(String type) {
        return Arrays.stream(values())
            .filter(missionType -> missionType.name().equalsIgnoreCase(type))
            .findFirst()
            .orElse(null);
    }
}

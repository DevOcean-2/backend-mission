package com.devocean.Balbalm.notification.domain.enumeration;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum NotificationType {
    TREASURE_HUNT,
    LANDMARK,
    FEED,
    DISCONNECT;

    public static NotificationType findNotificationType(String type) {
        return Arrays.stream(values())
                .filter(notificationType -> notificationType.name().equalsIgnoreCase(type))
                .findFirst()
                .orElse(null);
    }
}

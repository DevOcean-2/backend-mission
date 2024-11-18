package com.devocean.Balbalm.notification.domain.entity;

import com.devocean.Balbalm.global.enumeration.ResultCode;
import com.devocean.Balbalm.global.exception.CommonException;
import com.devocean.Balbalm.notification.domain.enumeration.NotificationStatus;
import com.devocean.Balbalm.notification.domain.enumeration.NotificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "notification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Column(name = "to_user_id")
    private String toUserId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type")
    private NotificationType notificationType;

    @Embedded
    private NotificationContent notificationContent;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "notification_status")
    private NotificationStatus notificationStatus = NotificationStatus.UNREAD;

    public void read(String toUserId) {
        if (!this.toUserId.equals(toUserId)) {
            throw new CommonException(ResultCode.FAIL);
        }
        this.notificationStatus = NotificationStatus.READ;
    }

    private Notification(String toUserId, NotificationType notificationType, NotificationContent notificationContent) {
        this.toUserId = toUserId;
        this.notificationType = notificationType;
        this.notificationContent = notificationContent;
    }

    public static Notification create(String toUserId, NotificationType notificationType, NotificationContent notificationContent) {
        return new Notification(toUserId, notificationType, notificationContent);
    }
}
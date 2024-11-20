package com.devocean.Balbalm.notification.usecase;

import com.devocean.Balbalm.global.UseCase;
import com.devocean.Balbalm.global.util.JwtUtil;
import com.devocean.Balbalm.notification.dataprovider.NotificationDataProvider;
import com.devocean.Balbalm.notification.domain.entity.NotificationContent;
import com.devocean.Balbalm.notification.domain.enumeration.NotificationStatus;
import com.devocean.Balbalm.notification.domain.enumeration.NotificationType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetNotificationListUseCase implements UseCase<GetNotificationListUseCase.Command, GetNotificationListUseCase.Result> {

    private final NotificationDataProvider notificationDataProvider;
    private final JwtUtil jwtUtil;

    @Override
    public Result execute(Command input) {
        String userId = jwtUtil.extractSocialId(input.getToken());
        notificationDataProvider.getNotificationList(userId);
        return null;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Command implements Serializable, UseCase.Command {

        @Serial
        private static final long serialVersionUID = -8721066978435580694L;

        private String token;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result implements Serializable, UseCase.Result {

        @Serial
        private static final long serialVersionUID = 1910516317000499984L;

        private Long id;
        private String toUserId;
        private NotificationType notificationType;
        private NotificationContent notificationContent;
        private NotificationStatus notificationStatus;
    }
}

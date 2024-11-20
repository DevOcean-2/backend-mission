package com.devocean.Balbalm.notification.controller;

import com.devocean.Balbalm.global.exception.CommonResponse;
import com.devocean.Balbalm.global.util.JwtUtil;
import com.devocean.Balbalm.mission.domain.enumeration.MissionType;
import com.devocean.Balbalm.notification.domain.enumeration.NotificationType;
import com.devocean.Balbalm.notification.service.NotificationService;

import com.devocean.Balbalm.notification.usecase.GetNotificationListUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Tag(name = "알림 관련 API", description = "알림 구독 및 취소, 알림 목록 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService notificationService;
    private final GetNotificationListUseCase getNotificationListUseCase;
    private final JwtUtil jwtUtil;

    @Operation(summary = "이벤트 구독")
    @GetMapping(value = "/subscribe")
    public SseEmitter subscribe(@RequestHeader("Authorization") String token,
                                @RequestParam("missionType") MissionType missionType) {
        String userId = jwtUtil.extractSocialId(token.substring(7));
        return notificationService.subscribe(userId, missionType);
    }

    @Operation(summary = "이벤트 구독 취소")
    @DeleteMapping(value = "/unsubscribe")
    public CommonResponse<Void> unSubscribe(@RequestHeader("Authorization") String token,
            @RequestParam("missionType") MissionType missionType
    ) {
        String userId = jwtUtil.extractSocialId(token.substring(7));
        notificationService.unsubscribe(userId, missionType);
        return CommonResponse.success();
    }

    @Operation(summary = "알림 목록 조회")
    @GetMapping("/list")
    public CommonResponse<GetNotificationListUseCase.Result> getNotificationList(@RequestHeader("Authorization") String token) {
        return CommonResponse.success(getNotificationListUseCase.execute(new GetNotificationListUseCase.Command(token.substring(7))));
    }
}
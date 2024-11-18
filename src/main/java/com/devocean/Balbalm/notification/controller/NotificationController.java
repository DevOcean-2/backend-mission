package com.devocean.Balbalm.notification.controller;

import com.devocean.Balbalm.global.exception.CommonResponse;
import com.devocean.Balbalm.notification.domain.enumeration.NotificationType;
import com.devocean.Balbalm.notification.service.NotificationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Tag(name = "알림 관련 API", description = "알림 구독 및 취소 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "이벤트 구독")
    @GetMapping(value = "/subscribe")
    public SseEmitter subscribe(@RequestParam("userId") String userId, @RequestParam("notificationType") NotificationType notificationType) {
        return notificationService.subscribe(userId, notificationType);
    }

    @Operation(summary = "이벤트 구독 취소")
    @DeleteMapping(value = "/unsubscribe")
    public CommonResponse<Void> unSubscribe(
            @RequestHeader("userId") String userId, @RequestParam("notificationType") NotificationType notificationType
    ) {
        notificationService.unsubscribe(userId);
        return CommonResponse.success();
    }
}
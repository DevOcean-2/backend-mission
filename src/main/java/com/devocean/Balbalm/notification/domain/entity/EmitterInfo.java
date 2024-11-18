package com.devocean.Balbalm.notification.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmitterInfo {
    private String userId;
    private SseEmitter emitter;
}


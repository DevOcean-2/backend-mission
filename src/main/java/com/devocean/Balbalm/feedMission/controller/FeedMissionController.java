package com.devocean.Balbalm.feedMission.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devocean.Balbalm.feedMission.controller.dto.FeedMissionRequestDto;
import com.devocean.Balbalm.feedMission.service.FeedMissionService;
import com.devocean.Balbalm.global.exception.CommonResponse;
import com.devocean.Balbalm.global.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/feed-mission")
@RequiredArgsConstructor
public class FeedMissionController {
	private final JwtUtil jwtUtil;
	private final FeedMissionService feedMissionService;
	@PostMapping()
	public CommonResponse<Boolean> checkMissionComplete(@RequestHeader("Authorization") String token, @RequestBody FeedMissionRequestDto requestDto) {
		String userId = jwtUtil.extractSocialId(token);

		boolean isCompleted = feedMissionService.checkMission(requestDto.getPostId(), requestDto.getMissionId(), userId);
		return new CommonResponse<>(isCompleted);
	}
}

package com.devocean.Balbalm.feedMission.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devocean.Balbalm.feedMission.controller.dto.FeedMissionRequestDto;
import com.devocean.Balbalm.feedMission.controller.dto.MissionResponseDto;
import com.devocean.Balbalm.feedMission.service.FeedMissionService;
import com.devocean.Balbalm.global.exception.CommonResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/feed-mission")
@RequiredArgsConstructor
public class FeedMissionController {
	private final FeedMissionService feedMissionService;
	@PostMapping()
	public CommonResponse<Boolean> checkMissionComplete(@RequestHeader("Authorization") String token, @RequestBody FeedMissionRequestDto requestDto) {
		boolean isCompleted = feedMissionService.checkMission(requestDto.getHashTag(), requestDto.getMissionId(), token.substring(7)).block();
		return new CommonResponse<>(isCompleted);
	}

	@GetMapping()
	public CommonResponse<MissionResponseDto> getMissionByMonth(@RequestHeader("Authorization") String token, @RequestParam int year, @RequestParam int month) {
		return new CommonResponse<>(feedMissionService.getMissionByMonth(year, month, token.substring(7)));
	}
}

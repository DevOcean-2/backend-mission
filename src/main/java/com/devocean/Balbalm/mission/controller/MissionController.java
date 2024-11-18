package com.devocean.Balbalm.mission.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devocean.Balbalm.global.exception.CommonResponse;
import com.devocean.Balbalm.mission.usecase.GetMissionListUseCase;
import com.devocean.Balbalm.mission.usecase.LandMarkMissionUseCase;
import com.devocean.Balbalm.mission.usecase.TreasureHuntMissionUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MissionController {

	private final TreasureHuntMissionUseCase treasureHuntMissionUseCase;
	private final LandMarkMissionUseCase landMarkMissionUseCase;
	private final GetMissionListUseCase getMissionListUseCase;

	@PostMapping("/treasure-hunt")
	public CommonResponse<TreasureHuntMissionUseCase.Result> checkTreasureHuntMission(@RequestHeader("Authorization") String token,
		@RequestBody TreasureHuntMissionUseCase.Command command) {
		command.setToken(token.substring(7));
		return CommonResponse.success(treasureHuntMissionUseCase.execute(command));
	}

	@PostMapping("/landmark")
	public CommonResponse<LandMarkMissionUseCase.Result> checkLandMarkMission(@RequestHeader("Authorization") String token,
		@RequestBody LandMarkMissionUseCase.Command command) {
		command.setToken(token.substring(7));
		return CommonResponse.success(landMarkMissionUseCase.execute(command));
	}

	@GetMapping("/list")
	public CommonResponse<GetMissionListUseCase.Result> getMissionList(@RequestHeader("Authorization") String token) {
		return CommonResponse.success(getMissionListUseCase.execute(new GetMissionListUseCase.Command(token.substring(7))));
	}
}

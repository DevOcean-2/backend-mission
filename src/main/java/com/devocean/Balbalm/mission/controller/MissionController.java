package com.devocean.Balbalm.mission.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "미션 관련 API", description = "미션 체크, 미션 목록 조회 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MissionController {

	private final TreasureHuntMissionUseCase treasureHuntMissionUseCase;
	private final LandMarkMissionUseCase landMarkMissionUseCase;
	private final GetMissionListUseCase getMissionListUseCase;

	@Operation(summary = "보물찾기 미션 체크")
	@PostMapping("/treasure-hunt")
	public CommonResponse<TreasureHuntMissionUseCase.Result> checkTreasureHuntMission(@RequestHeader("Authorization") String token,
		@RequestBody TreasureHuntMissionUseCase.Command command) {
		command.setToken(token.substring(7));
		return CommonResponse.success(treasureHuntMissionUseCase.execute(command));
	}

	@Operation(summary = "랜드마크 방문 미션 체크")
	@PostMapping("/landmark")
	public CommonResponse<LandMarkMissionUseCase.Result> checkLandMarkMission(@RequestHeader("Authorization") String token,
		@RequestBody LandMarkMissionUseCase.Command command) {
		command.setToken(token.substring(7));
		return CommonResponse.success(landMarkMissionUseCase.execute(command));
	}


	@Operation(summary = "알림 목록 조회")
	@GetMapping("/list")
	public CommonResponse<GetMissionListUseCase.Result> getMissionList(@RequestHeader("Authorization") String token) {
		return CommonResponse.success(getMissionListUseCase.execute(new GetMissionListUseCase.Command(token.substring(7))));
	}
}

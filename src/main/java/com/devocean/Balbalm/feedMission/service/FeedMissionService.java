package com.devocean.Balbalm.feedMission.service;

import org.springframework.stereotype.Service;

import com.devocean.Balbalm.feedMission.entity.FeedMissionUser;
import com.devocean.Balbalm.feedMission.repository.FeedMissionUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedMissionService {
	private final FeedMissionUserRepository feedMissionUserRepository;
	public boolean checkMission(Long postId, Long missionId, String userId) {

		// 피드 조회 API 호출해서 피드 정보 호출

		// 조회한 피드에서 해시태그 정보가 mission_id에 해당하는 해시태그와 같은지

		// 같다면 미션 완료 업데이트
		FeedMissionUser missionStatus = FeedMissionUser.builder()
			.feedMissionId(postId)
			.userId(userId)
			.percent(100)
			.build();
		feedMissionUserRepository.save(missionStatus);
		return true;
	}
}

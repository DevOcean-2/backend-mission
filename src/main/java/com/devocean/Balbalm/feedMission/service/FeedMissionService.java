package com.devocean.Balbalm.feedMission.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.devocean.Balbalm.feedMission.controller.dto.MissionResponseDto;
import com.devocean.Balbalm.feedMission.controller.dto.PostDto;
import com.devocean.Balbalm.feedMission.entity.FeedMission;
import com.devocean.Balbalm.feedMission.entity.FeedMissionUser;
import com.devocean.Balbalm.feedMission.entity.HashTag;
import com.devocean.Balbalm.feedMission.repository.FeedMissionRepository;
import com.devocean.Balbalm.feedMission.repository.FeedMissionUserRepository;
import com.devocean.Balbalm.global.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedMissionService {
	private final WebClient.Builder webClientBuilder;
	private final FeedMissionRepository feedMissionRepository;
	private final FeedMissionUserRepository feedMissionUserRepository;
	private final JwtUtil jwtUtil;
	@Value("${api.external.base-url}")
	private String apiBaseUrl;

	public Mono<Boolean> checkMission(String hashTag, Long missionId, String token) {
		// 피드 조회 API 호출해서 피드 정보 호출
		String userId = jwtUtil.extractSocialId(token);

		AtomicBoolean missionComplete = new AtomicBoolean(false);
		return getPostByHashTag(hashTag, token)
			.flatMap(posts -> {
				posts.forEach(post -> {
					if (post.getUserId().equals(userId)) {
						missionComplete.set(true);
					}
				});

				FeedMission mission = feedMissionRepository.findById(missionId)
					.orElseThrow(() -> new RuntimeException());

				List<HashTag> hashTags = mission.getHashTags();
				boolean isValid = hashTags.stream().anyMatch(h -> h.getName().equals(hashTag));

				if (!isValid) return Mono.just(false);

				if (!missionComplete.get()) {
					return Mono.just(false);
				}

				FeedMissionUser missionStatus = FeedMissionUser.builder()
					.feedMissionId(missionId)
					.userId(userId)
					.percent(100)
					.build();
				feedMissionUserRepository.save(missionStatus);
				return Mono.just(true);
			});
	}

	private Mono<List<PostDto>> getPostByHashTag(String hashTag, String token) {
		return webClientBuilder.baseUrl(apiBaseUrl+"/"+hashTag)
			.build()
			.get()
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
			.retrieve()
			.bodyToFlux(PostDto.class)
			.collectList();
	}

	public MissionResponseDto getMissionByMonth(int month) {
		FeedMission mission = feedMissionRepository.findByMonth(month)
			.orElseThrow(() -> new RuntimeException("Mission Not Found"));

		List<String> hashtags = mission.getHashTags().stream()
			.map(hashTag -> hashTag.getName()).collect(Collectors.toList());
		return MissionResponseDto.builder()
			.month(month)
			.hashtag(hashtags)
			.mission(mission.getContent())
			.build();
	}
}

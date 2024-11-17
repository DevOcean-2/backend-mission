package com.devocean.Balbalm.feedMission.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.devocean.Balbalm.feedMission.controller.dto.PostDto;
import com.devocean.Balbalm.feedMission.entity.FeedMissionUser;
import com.devocean.Balbalm.feedMission.repository.FeedMissionUserRepository;
import com.devocean.Balbalm.global.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FeedMissionService {
	private final WebClient.Builder webClientBuilder;
	private final FeedMissionUserRepository feedMissionUserRepository;
	private final JwtUtil jwtUtil;
	@Value("${api.external.base-url}")  // yml에서 정의한 값 주입
	private String apiBaseUrl;

	public boolean checkMission(String hashTag, Long missionId, String token) {
		// 조회한 피드에서 해시태그 정보가 mission_id에 해당하는 해시태그와 같은지


		// 피드 조회 API 호출해서 피드 정보 호출
		String userId = jwtUtil.extractSocialId(token);
		getPostByHashTag(hashTag, token)
			.subscribe(posts -> {
				posts.forEach(post -> {
					// 여기서 userId가 있는지 확인하기
					System.out.println("Post ID: " + post.getPostId());
					System.out.println("User ID: " + post.getUserId());
				});
			});


		// 같다면 미션 완료 업데이트
		FeedMissionUser missionStatus = FeedMissionUser.builder()
			.feedMissionId(missionId)
			.userId(userId)
			.percent(100)
			.build();
		feedMissionUserRepository.save(missionStatus);
		return true;
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
}

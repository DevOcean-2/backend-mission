package com.devocean.Balbalm.feedMission.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDto {
	private Long postId;
	private String userId;
	private List<String> imageUrls;
	private String content;
	private LocalDateTime uploadedAt;
	private List<LikedBy> likedBy;


	@Getter
	@AllArgsConstructor
	public static class LikedBy {
		private String userId;
		private String nickname;
		private String profileImageUrl;
	}
}

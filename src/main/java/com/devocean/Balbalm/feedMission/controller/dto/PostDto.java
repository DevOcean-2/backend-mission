package com.devocean.Balbalm.feedMission.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	@JsonProperty("post_id")
	private Long postId;
	@JsonProperty("user_id")
	private String userId;
}

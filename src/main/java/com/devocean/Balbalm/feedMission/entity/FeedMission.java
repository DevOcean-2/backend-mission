package com.devocean.Balbalm.feedMission.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

@Getter
@Entity
public class FeedMission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String content;
	@OneToMany(mappedBy = "feedMission")
	private List<HashTag> hashTags;
}

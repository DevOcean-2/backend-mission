package com.devocean.Balbalm.mission.domain.entity;

import java.time.LocalDateTime;

import com.devocean.Balbalm.global.domain.BaseEntity;
import com.devocean.Balbalm.mission.domain.enumeration.MissionProgressType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Setter
@Entity
@Table(name = "land_mark_mission")
@NoArgsConstructor
@AllArgsConstructor
public class LandMarkMission extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "land_mark_mission_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "location_mission_id")
	private LocationMission locationMission;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "count")
	private int count;   // targetCount == count -> mission complete

	@Column(name = "percent")
	private int percent;

	@Column(name = "progress_type")
	@Enumerated(EnumType.STRING)
	private MissionProgressType progressType = MissionProgressType.PROGRESS;

	@Column(name = "is_complete")
	private boolean isComplete;

	@Column(name = "complete_date")
	private LocalDateTime completeDate;
}
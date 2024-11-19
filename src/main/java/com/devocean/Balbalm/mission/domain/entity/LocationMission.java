package com.devocean.Balbalm.mission.domain.entity;

import java.time.LocalDate;

import com.devocean.Balbalm.global.domain.BaseEntity;
import com.devocean.Balbalm.mission.domain.enumeration.MissionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "location_mission")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationMission extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "location_mission_id")
	private Long id;

	@Column(name = "mission_type")
	@Enumerated(EnumType.STRING)
	private MissionType missionType;

	@Column(name = "location_name")
	private String locationName;

	@Column(name = "missionName_name")
	private String missionName;

	@Column(name = "description")
	private String description;

	@Column(name = "target_count")
	private int targetCount;

	@Column(name = "latitude")
	private double latitude;

	@Column(name = "longitude")
	private double longitude;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	@Column(name = "complete_assign")
	private boolean completeAssign;
}

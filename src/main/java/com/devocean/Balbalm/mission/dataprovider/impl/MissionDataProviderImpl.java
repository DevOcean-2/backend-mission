package com.devocean.Balbalm.mission.dataprovider.impl;

import static com.devocean.Balbalm.global.enumeration.ResultCode.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.devocean.Balbalm.global.exception.CommonException;
import com.devocean.Balbalm.mission.dataprovider.MissionDataProvider;
import com.devocean.Balbalm.mission.domain.UserMissionInfo;
import com.devocean.Balbalm.mission.domain.entity.LandMarkMission;
import com.devocean.Balbalm.mission.domain.entity.LocationMission;
import com.devocean.Balbalm.mission.domain.entity.TreasureMission;
import com.devocean.Balbalm.mission.domain.enumeration.LandMarkMissionProgressType;
import com.devocean.Balbalm.mission.domain.enumeration.MissionProgressType;
import com.devocean.Balbalm.mission.domain.enumeration.MissionType;
import com.devocean.Balbalm.mission.repository.LandMarkMissionRepository;
import com.devocean.Balbalm.mission.repository.LocationMissionRepository;
import com.devocean.Balbalm.mission.repository.TreasureMissionRepository;
import com.devocean.Balbalm.mission.domain.MissionInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MissionDataProviderImpl implements MissionDataProvider {

	private final LocationMissionRepository locationMissionRepository;
	private final TreasureMissionRepository treasureMissionRepository;
	private final LandMarkMissionRepository landMarkMissionRepository;

	@Override
	public List<UserMissionInfo> getMissionList(String userId, LocalDate today) {
		List<LocationMission> locationMissionList = locationMissionRepository.findByCurrentDate(today);

		List<UserMissionInfo> userMissionInfoList = new ArrayList<>();
		for (LocationMission locationMission : locationMissionList) {
			Long missionId = locationMission.getId();
			if (MissionType.TREASURE_HUNT.equals(locationMission.getMissionType())) {
				TreasureMission treasureMission = treasureMissionRepository.findByUserIdAndLocationMissionId(userId, missionId)
						.orElseThrow(() -> new CommonException(NOT_FOUND_MISSION));

				UserMissionInfo userMissionInfo = UserMissionInfo.builder()
						.missionId(missionId)
						.userMissionId(treasureMission.getId())
						.missionName(locationMission.getMissionName())
						.missionType(locationMission.getMissionType())
						.percent(treasureMission.getPercent())
						.missionProgressType(treasureMission.getProgressType())
						.isComplete(treasureMission.isComplete())
						.completeDate(treasureMission.getCompleteDate() != null ? treasureMission.getCompleteDate().toLocalDate() : null)
						.build();

				userMissionInfoList.add(userMissionInfo);

			} else if (MissionType.LANDMARK.equals(locationMission.getMissionType())) {
				LandMarkMission landMarkMission = landMarkMissionRepository.findByUserIdAndLocationMissionId(userId, missionId);
				if (ObjectUtils.isEmpty(landMarkMission)) {
					throw new CommonException(NOT_FOUND_MISSION);
				}

				UserMissionInfo userMissionInfo = UserMissionInfo.builder()
						.missionId(missionId)
						.userMissionId(landMarkMission.getId())
						.missionName(locationMission.getMissionName())
						.missionType(locationMission.getMissionType())
						.count(landMarkMission.getCount())
						.percent(landMarkMission.getPercent())
						.missionProgressType(landMarkMission.getProgressType())
						.isComplete(landMarkMission.isComplete())
						.completeDate(landMarkMission.getCompleteDate() != null ? landMarkMission.getCompleteDate().toLocalDate() : null)
						.build();

				userMissionInfoList.add(userMissionInfo);
			}
		}
		return userMissionInfoList;
	}

	@Override
	public List<MissionInfo> getMissionInfoList(LocalDate today, MissionType missionType) {
		List<LocationMission> targetMissionList = locationMissionRepository.findByMissionTypeAndCurrentDate(today, missionType);
		if (ObjectUtils.isEmpty(targetMissionList)) {
			return new ArrayList<>();
		}
		return targetMissionList.stream().map(MissionDataProviderImplMapper.MAPPER::toMissionInfo).toList();
	}

	@Override
	public UserMissionInfo getUserMissionInfo(String userId, MissionType missionType, Long missionId) {
		if (MissionType.TREASURE_HUNT.equals(missionType)) {
			TreasureMission treasureMission = treasureMissionRepository.findByUserIdAndLocationMissionId(userId, missionId)
					.orElseThrow(() -> new CommonException(NOT_FOUND_MISSION));

			return UserMissionInfo.builder()
					.missionId(missionId)
					.userMissionId(treasureMission.getId())
					.locationName(treasureMission.getLocationMission().getLocationName())
					.missionName(treasureMission.getLocationMission().getMissionName())
					.missionType(missionType)
					.count(1)
					.percent(100)
					.missionProgressType(treasureMission.getProgressType())
					.isComplete(treasureMission.isComplete())
					.completeDate(treasureMission.getCompleteDate() != null ? treasureMission.getCompleteDate().toLocalDate() : null)
					.build();

		} else if (MissionType.LANDMARK.equals(missionType)) {
			LandMarkMission landMarkMission = landMarkMissionRepository.findByUserIdAndLocationMissionId(userId, missionId);
			if (ObjectUtils.isEmpty(landMarkMission)) {
				throw new CommonException(NOT_FOUND_MISSION);
			}

			return UserMissionInfo.builder()
					.missionId(missionId)
					.userMissionId(landMarkMission.getId())
					.locationName(landMarkMission.getLocationMission().getLocationName())
					.missionName(landMarkMission.getLocationMission().getMissionName())
					.missionType(missionType)
					.count(landMarkMission.getCount())
					.percent(landMarkMission.getPercent())
					.missionProgressType(landMarkMission.getProgressType())
					.isComplete(landMarkMission.isComplete())
					.completeDate(landMarkMission.getCompleteDate() != null ? landMarkMission.getCompleteDate().toLocalDate() : null)
					.build();
		} else {
			return new UserMissionInfo();
		}
	}

	@Override
	public double getMissionDistance(double currentLatitude, double currentLongitude, double targetLatitude, double targetLongitude) {
		return getDistance(currentLatitude, currentLongitude, targetLatitude, targetLongitude);
	}

	@Override
	public void completeTreasureMission(String userId, Long missionId) {
		TreasureMission treasureMission = treasureMissionRepository.findByUserIdAndLocationMissionId(userId, missionId)
				.orElseThrow(() -> new CommonException(NOT_FOUND_MISSION));

		treasureMission.setPercent(100);
		treasureMission.setProgressType(MissionProgressType.COMPLETE);
		treasureMission.setComplete(true);
		treasureMission.setCompleteDate(LocalDateTime.now());

		treasureMissionRepository.save(treasureMission);
		treasureMissionRepository.flush();
	}

	@Override
	public List<MissionInfo> getNewMissionList(LocalDate today) {
		List<LocationMission> newMissionList = locationMissionRepository.findByCurrentDateAndCompleteAssign(today, false);
		return newMissionList.stream().map(MissionDataProviderImplMapper.MAPPER::toMissionInfo).toList();
	}

	@Override
	public void updateNewMissionList(List<Long> locationMissionIds) {
		locationMissionRepository.updateMissionCompleteAssign(locationMissionIds);
	}

	@Override
	public void assignNewTreasureHuntMission(Long locationMissionId, List<String> userIds) {
		LocationMission targetLocationMission = locationMissionRepository.findById(locationMissionId)
				.orElseThrow(() -> new CommonException(NOT_FOUND_MISSION));

		List<TreasureMission> treasureMissionList = new ArrayList<>();

		for (String userId : userIds) {
			Optional<TreasureMission> optionalTreasureMission = treasureMissionRepository.findByUserIdAndLocationMissionId(userId, locationMissionId);
			if (optionalTreasureMission.isPresent()) {
				continue;
			}
			treasureMissionList.add(
					TreasureMission.builder()
							.userId(userId)
							.locationMission(targetLocationMission)
							.progressType(MissionProgressType.PROGRESS)
							.isComplete(false)
							.build()
			);
		}

		treasureMissionRepository.saveAll(treasureMissionList);
	}

	@Override
	public void assignNewLandMarkMission(Long missionId, List<String> userIds) {
		LocationMission targetLocationMission = locationMissionRepository.findById(missionId)
				.orElseThrow(() -> new CommonException(NOT_FOUND_MISSION));

		List<LandMarkMission> landMarkMissionList = new ArrayList<>();

		for (String userId : userIds) {
			LandMarkMission landMarkMission = landMarkMissionRepository.findByUserIdAndLocationMissionId(userId, missionId);
			if (!ObjectUtils.isEmpty(landMarkMission)) {
				continue;
			}

			landMarkMissionList.add(
					LandMarkMission.builder()
							.userId(userId)
							.locationMission(targetLocationMission)
							.count(0)
							.percent(LandMarkMissionProgressType.ZERO.getPercent())
							.progressType(MissionProgressType.PROGRESS)
							.isComplete(false)
							.build()
			);
		}

		landMarkMissionRepository.saveAll(landMarkMissionList);
	}

	@Override
	public void completeAssignNewMission(List<Long> missionIds) {
		locationMissionRepository.updateMissionCompleteAssign(missionIds);
	}

	@Override
	public void updateLandMarkMissionCount(String userId, Long missionId) {
		LandMarkMission landMarkMission = landMarkMissionRepository.findByUserIdAndLocationMissionId(userId, missionId);
		if (ObjectUtils.isEmpty(landMarkMission)) {
//			throw new CommonException(NOT_FOUND_MISSION);
			return;
		}

		int plusCount = landMarkMission.getCount() + 1;
		landMarkMission.setCount(plusCount);
		landMarkMission.setPercent(LandMarkMissionProgressType.findProgressType(plusCount).getPercent());

		if (plusCount == landMarkMission.getLocationMission().getTargetCount()) {
			landMarkMission.setProgressType(MissionProgressType.COMPLETE);
			landMarkMission.setComplete(true);
			landMarkMission.setCompleteDate(LocalDateTime.now());
		}

		landMarkMissionRepository.save(landMarkMission);
		landMarkMissionRepository.flush();
	}

	private static double getDistance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

		dist = Math.acos(dist);
		dist = rad2deg(dist);

		dist = dist * 60 * 1.1515 * 1609.344;

		return dist;  // 단위 m
	}

	// 10진수를 radian 으로 변환
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	// radian 을 10진수로 변환
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	@Override
	public List<UserMissionInfo> getUserMissionInfoList(String userId, MissionType missionType) {
		LocalDate today = LocalDate.now();

		if (MissionType.TREASURE_HUNT.equals(missionType)) {
			List<Long> locationMissionIds = locationMissionRepository.findByMissionTypeAndCurrentDate(today, MissionType.TREASURE_HUNT)
					.stream().map(LocationMission::getId).toList();
			List<TreasureMission> treasureMissionList = treasureMissionRepository.findAllByUserIdAndLocationMissionIds(userId, locationMissionIds);

			return treasureMissionList.stream()
					.map(treasureMission ->
							UserMissionInfo.builder()
									.missionId(treasureMission.getLocationMission().getId())
									.userMissionId(treasureMission.getId())
									.locationName(treasureMission.getLocationMission().getLocationName())
									.missionName(treasureMission.getLocationMission().getMissionName())
									.missionType(treasureMission.getLocationMission().getMissionType())
									.latitude(treasureMission.getLocationMission().getLatitude())
									.longitude(treasureMission.getLocationMission().getLongitude())
									.count(1)
									.percent(treasureMission.getPercent())
									.missionProgressType(treasureMission.getProgressType())
									.isComplete(treasureMission.isComplete())
									.completeDate(treasureMission.getCompleteDate() != null ? treasureMission.getCompleteDate().toLocalDate() : null)
									.build()
					).toList();

		} else if (MissionType.LANDMARK.equals(missionType)) {
			List<Long> locationMissionIds = locationMissionRepository.findByMissionTypeAndCurrentDate(today, MissionType.LANDMARK)
					.stream().map(LocationMission::getId).toList();
			List<LandMarkMission> landMarkMissionList = landMarkMissionRepository.findAllByUserIdAndLocationMissionIds(userId, locationMissionIds);

			return landMarkMissionList.stream()
					.map(landMarkMission ->
							UserMissionInfo.builder()
									.missionId(landMarkMission.getLocationMission().getId())
									.userMissionId(landMarkMission.getId())
									.locationName(landMarkMission.getLocationMission().getLocationName())
									.missionName(landMarkMission.getLocationMission().getMissionName())
									.missionType(landMarkMission.getLocationMission().getMissionType())
									.latitude(landMarkMission.getLocationMission().getLatitude())
									.longitude(landMarkMission.getLocationMission().getLongitude())
									.count(landMarkMission.getCount())
									.percent(landMarkMission.getPercent())
									.missionProgressType(landMarkMission.getProgressType())
									.isComplete(landMarkMission.isComplete())
									.completeDate(landMarkMission.getCompleteDate() != null ? landMarkMission.getCompleteDate().toLocalDate() : null)
									.build()
					).toList();
		}
		return new ArrayList<>();
	}

	@Mapper
	public interface MissionDataProviderImplMapper {
		MissionDataProviderImpl.MissionDataProviderImplMapper MAPPER = Mappers.getMapper(MissionDataProviderImpl.MissionDataProviderImplMapper.class);

		@Mapping(target = "missionId", source = "id")
		MissionInfo toMissionInfo(LocationMission locationMission);

		List<MissionInfo> toMissionInfoList(List<LocationMission> locationMission);
	}
}